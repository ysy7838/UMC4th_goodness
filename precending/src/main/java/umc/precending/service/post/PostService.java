package umc.precending.service.post;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.precending.domain.Post.NewsPost;
import umc.precending.domain.Post.NormalPost;
import umc.precending.domain.Post.Post;
import umc.precending.domain.Post.RecommendPost;
import umc.precending.domain.category.Category;
import umc.precending.domain.category.PostCategory;
import umc.precending.domain.image.PostImage;
import umc.precending.domain.member.Corporate;
import umc.precending.domain.member.Member;
import umc.precending.dto.post.*;
import umc.precending.exception.category.CategoryNotFoundException;
import umc.precending.exception.member.MemberNotFoundException;
import umc.precending.exception.post.PostAuthException;
import umc.precending.exception.post.PostNewsNotSupportedException;
import umc.precending.exception.post.PostNotFoundException;
import umc.precending.exception.post.PostVerifyException;
import umc.precending.repository.category.CategoryRepository;
import umc.precending.repository.member.MemberRepository;
import umc.precending.repository.post.PostRepository;
import umc.precending.service.image.ImageService;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    @Value("${crawling.driver}")
    private String driverUrl;

    // 사용자가 작성한 전체 게시글 보기 - 월 단위
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAll(int year, int month, Member member) {
        List<Post> postList = postRepository.findAllByWriterAndYearAndMonth(member.getUsername(), year, month);
        if(postList.isEmpty()) throw new PostNotFoundException();

        return postList.stream()
                .map(post -> new PostResponseDto<>().toDto(post))
                .collect(Collectors.toList());
    }

    // 단일 게시글 보기
    @Transactional(readOnly = true)
    public PostResponseDto findOne(Long id) {
        Post findPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        return new PostResponseDto().toDto(findPost);
    }

    // 인증 가능한 게시글만을 조회하는 로직
    @Transactional
    public List<PostResponseDto> findVerifiablePost() {
        List<Post> postList = postRepository.findPostsByVerifiableAndIsVerified(true, false);

        if(postList.isEmpty()) throw new PostNotFoundException();

        return postList.stream()
                .map(post -> new PostResponseDto().toDto(post))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getCorporatePost() {
        List<Post> recentPostList = getCorporatePosts();
        if(recentPostList.isEmpty()) throw new PostNotFoundException();

        return recentPostList.stream()
                .map(value -> new PostResponseDto<>().toDto(value))
                .collect(Collectors.toList());
    }

    // 인증 가능한 게시글을 관리자가 판단하여 인증여부를 결정하는 로직
    @Transactional
    public void checkPostValidation(Long id) {
        Post findPost = getPost(id);
        checkValidation(findPost);
        findPost.verifyPost();
    }

    // 인증이 가능한 선행 기록 - 뉴스
    @Transactional
    public void makeValidateNews(PostNewsCreateDto createDto, Member member) {
        PostCrawlingResponseDto responseDto = crawlingData(createDto.getNewsUrl());
        List<PostCategory> categories = getCategories(createDto.getCategories());
        List<PostImage> images = getPostImages(responseDto);

        Post post = getNewsPost(createDto, responseDto, member, categories, images);

        addCategories(createDto, categories);

        postRepository.save(post);
    }

    // 인증이 가능한 선행 기록 - 텍스트 + 사진
    @Transactional
    public void makeValidateNormalPost(PostNormalCreateDto createDto, Member member) {
        List<PostImage> images = createDto.getFiles().stream()
                .map(PostImage::new).collect(Collectors.toList());
        List<PostCategory> categories = getCategories(createDto.getCategories());

        Post post = getValidateNormalPost(createDto, member, images, categories);
        addImages(createDto.getFiles(), images);
        addCategories(createDto, categories);

        postRepository.save(post);
    }

    // 인증이 불가능한 선행 기록 - 텍스트 + 사진
    @Transactional
    public void makeNormalPost(PostNormalCreateDto createDto, Member member) {
        List<PostImage> images = createDto.getFiles().stream()
                .map(PostImage::new).collect(Collectors.toList());
        List<PostCategory> categories = getCategories(createDto.getCategories());

        Post post = getNormalPost(createDto, member, images, categories);
        addImages(createDto.getFiles(), images);
        addCategories(createDto, categories);

        postRepository.save(post);
    }

    public void makeRecommendPost(RecommendPost recommendPost){
        postRepository.save(recommendPost);
    }

    // 선행 기록 수정
    @Transactional
    public void editPost(PostEditDto editDto, Member member, Long id) {
        Post findPost = getPost(id, member);
        editPost(findPost, editDto);
    }

    // 선행 기록 삭제
    @Transactional
    public void deletePost(Member member, Long id) {
        Post findPost = getPost(id, member);
        postRepository.delete(findPost);
    }

    private PostCrawlingResponseDto crawlingData(String accessUrl) {
        if(accessUrl.contains("naver")) return crawlingNaver(accessUrl);
        else if(accessUrl.contains("daum")) return crawlingDaum(accessUrl);
        throw new PostNewsNotSupportedException(accessUrl + "은 지원하지 않는 플랫폼입니다.");
    }

    private PostCrawlingResponseDto crawlingNaver(String accessUrl) {
        WebDriver driver = getChromDriver();

        try {
            driver.get(accessUrl);
            Thread.sleep(1000);

            List<WebElement> elements = driver.findElements(By.id("dic_area"));
            List<WebElement> images = driver.findElements(By.id("img1"));

            String content = elements.get(0).getText();
            String image = images.get(0).getAttribute("src");

            driver.close();
            driver.quit();

            return new PostCrawlingResponseDto(content, image);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private PostCrawlingResponseDto crawlingDaum(String accessUrl) {
        WebDriver driver = getChromDriver();

        try {
            driver.get(accessUrl);
            Thread.sleep(1000);

            List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"mArticle\"]/div[2]"));
            List<WebElement> images = driver.findElements(By.xpath("//*[@id=\"mArticle\"]/div[2]/div[2]/section/figure[1]/p/img"));

            String content = elements.get(0).getText();
            String image = images.get(0).getAttribute("src");

            driver.close();
            driver.quit();

            return new PostCrawlingResponseDto(content, image);

        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private WebDriver getChromDriver() {
        System.setProperty("webdriver.chrome.driver", driverUrl);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        chromeOptions.addArguments("--lang=ko");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setCapability("ignoreProtectedModeSettings", true);

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        return driver;
    }

    private Post editPost(Post post, PostEditDto editDto) {
        if(post instanceof NormalPost) return editNormalPost(post, editDto);
        return editNewsPost(post, editDto);
    }

    private Post editNormalPost(Post post, PostEditDto editDto) {
        if(!editDto.getFiles().isEmpty()) {
            List<PostImage> images = editDto.getFiles().stream().map(PostImage::new).collect(Collectors.toList());
            post.editImages(images);
            addImages(editDto.getFiles(), images);
        }
        if(!editDto.getCategories().isEmpty()) {
            List<PostCategory> categories = getCategories(editDto.getCategories());
            post.editCategories(categories);
            addCategories(editDto, categories);
        }

        ((NormalPost)post).editPost(editDto.getYear(), editDto.getMonth(), editDto.getDay(), editDto.getContent());

        return post;
    }

    private Post editNewsPost(Post post, PostEditDto editDto) {
        if(!editDto.getCategories().isEmpty()) {
            List<PostCategory> categories = getCategories(editDto.getCategories());
            post.editCategories(categories);
            addCategories(editDto, categories);
        }
        if(!editDto.getContent().isBlank()) {
            String newsUrl = editDto.getContent();
            PostCrawlingResponseDto responseDto = crawlingData(newsUrl);
            List<PostImage> images = getPostImages(responseDto);

            ((NewsPost)post).editPost(editDto.getYear(), editDto.getMonth(), editDto.getDay(),
                    responseDto.getAccessUrl(), responseDto.getContent());
            post.editImages(images);

            return post;
        }

        ((NewsPost)post).editPost(editDto.getYear(), editDto.getMonth(), editDto.getDay());

        return post;
    }

    private Post getPost(Long id, Member member) {
        Post findPost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        if(!checkValidation(member, findPost)) throw new PostAuthException();
        return findPost;
    }

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    private List<Post> getCorporatePosts() {
        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "firstCreatedDate"));

        return postList.stream()
                .filter(post ->
                        memberRepository
                                .findMemberByUsername(post.getWriter())
                                .orElseThrow(MemberNotFoundException::new) instanceof Corporate)
                .collect(Collectors.toList());
    }

    private boolean checkValidation(Member member, Post post) {
        return member.getUsername().equals(post.getWriter());
    }

    private void checkValidation(Post post) {
        if(!post.isVerifiable() || post.isVerified()) throw new PostVerifyException();
    }

    private List<PostCategory> getCategories(List<Long> categories) {
        return categories.stream()
                .map(id -> categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new))
                .map(PostCategory::new)
                .collect(Collectors.toList());
    }

    private void addImages(List<MultipartFile> files, List<PostImage> images) {
        IntStream.range(0, images.size()).forEach(index -> {
            MultipartFile file = files.get(index);
            PostImage image = images.get(index);
            String accessUrl = imageService.saveImage(file, image.getStoredName());
            image.setAccessUrl(accessUrl);
        });
    }

    private void addCategories(PostEditDto editDto, List<PostCategory> categories) {
        List<Long> categoryId = editDto.getCategories();

        IntStream.range(0, categoryId.size()).forEach(index -> {
            Long id = categoryId.get(index);
            PostCategory postCategory = categories.get(index);
            Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
            setPostCategory(category, postCategory, editDto);

            postCategory.initCategory(category);
        });
    }

    private void addCategories(PostNormalCreateDto createDto, List<PostCategory> categories) {
        List<Long> categoryId = createDto.getCategories();

        IntStream.range(0, categoryId.size()).forEach(index -> {
            Long id = categoryId.get(index);
            PostCategory postCategory = categories.get(index);
            Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
            setPostCategory(category, postCategory, createDto);

            postCategory.initCategory(category);
        });
    }

    private void addCategories(PostNewsCreateDto createDto, List<PostCategory> categories) {
        List<Long> categoryId = createDto.getCategories();

        IntStream.range(0, categoryId.size()).forEach(index -> {
            Long id = categoryId.get(index);
            PostCategory postCategory = categories.get(index);
            Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
            setPostCategory(category, postCategory, createDto);

            postCategory.initCategory(category);
        });
    }

    private void setPostCategory(Category category, PostCategory postCategory, PostNormalCreateDto createDto) {
        boolean type = createDto.isType();
        if(!category.getCategoryName().equals("기부") || type) {
            postCategory.updateCount(1);
            return;
        }

        Double donation = createDto.getDonation();

        postCategory.updateCount(1 + (donation / 10000) * 0.1);
    }

    private void setPostCategory(Category category, PostCategory postCategory, PostNewsCreateDto createDto) {
        boolean type = createDto.isType();
        if(!category.getCategoryName().equals("기부") || type) {
            postCategory.updateCount(1);
            return;
        }

        Double donation = createDto.getDonation();

        postCategory.updateCount(1 + (donation / 10000) * 0.1);
    }

    private void setPostCategory(Category category, PostCategory postCategory, PostEditDto editDto) {
        boolean type = editDto.isType();
        if(!category.getCategoryName().equals("기부") || type) {
            postCategory.updateCount(1);
            return;
        }

        Double donation = editDto.getDonation();

        postCategory.updateCount(1 + (donation / 10000) * 0.1);
    }

    private Post getValidateNormalPost(PostNormalCreateDto createDto, Member member,
                                       List<PostImage> images, List<PostCategory> categories) {
        return new NormalPost(member.getUsername(), createDto.getContent(), true,
                createDto.getYear(), createDto.getMonth(), createDto.getDay(), categories, images);
    }

    private Post getNormalPost(PostNormalCreateDto createDto, Member member,
                                       List<PostImage> images, List<PostCategory> categories) {
        return new NormalPost(member.getUsername(), createDto.getContent(), false,
                createDto.getYear(), createDto.getMonth(), createDto.getDay(), categories, images);
    }

    private Post getNewsPost(PostNewsCreateDto createDto, PostCrawlingResponseDto responseDto, Member member, List<PostCategory> categories, List<PostImage> images) {
        return new NewsPost(member.getUsername(), createDto.getNewsUrl(), responseDto.getContent(),
                createDto.getYear(), createDto.getMonth(), createDto.getDay(), categories, images);
    }

    private List<PostImage> getPostImages(PostCrawlingResponseDto responseDto) {
        return List.of(new PostImage(responseDto.getAccessUrl() + ".jpeg", responseDto.getAccessUrl()));
    }
}
