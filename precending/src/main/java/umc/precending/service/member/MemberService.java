package umc.precending.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.precending.domain.Post.Post;
import umc.precending.domain.category.PostCategory;
import umc.precending.domain.image.Image;
import umc.precending.domain.image.MemberImage;
import umc.precending.domain.member.Member;
import umc.precending.dto.postCategory.PostCategoryResponseDto;
import umc.precending.exception.post.PostNotFoundException;
import umc.precending.repository.category.CategoryRepository;
import umc.precending.repository.post.PostRepository;
import umc.precending.repository.postCategory.PostCategoryRepository;
import umc.precending.service.image.ImageService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final ImageService imageService;
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PostCategoryRepository postCategoryRepository;

    // 회원의 프로필을 설정하는 로직
    @Transactional
    public void saveImage(MultipartFile file, Member member) {
        Image image = getMemberImage(file);
        saveMemberImage(file, image);

        member.saveImage(List.of((MemberImage)image));
    }

    @Transactional
    public List<PostCategoryResponseDto> getValidPostStatus(Member member) {
        List<Post> postList = getValidPostList(member);
        return getPostCategoryResponseList(postList);
    }

    @Transactional
    public List<PostCategoryResponseDto> getNormalPostStatus(Member member) {
        List<Post> postList = getNormalPostList(member);
        return getPostCategoryResponseList(postList);
    }

    private List<PostCategoryResponseDto> getPostCategoryResponseList(List<Post> postList) {
        List<PostCategoryResponseDto> postCategoryList = categoryRepository.findAll().stream()
                .map(PostCategoryResponseDto::new)
                .collect(Collectors.toList());

        getPostCategoryValues(postCategoryList, postList);

        return postCategoryList;
    }

    private void getPostCategoryValues(List<PostCategoryResponseDto> postCategoryList, List<Post> postList) {
        Map<String, Double> postMap = getPostMap(postList);

        postCategoryList.forEach(postCategory -> {
            String key = postCategory.getCategoryName();
            Double value = postMap.getOrDefault(key, 0.0);
            postCategory.addValue(value);
        });
    }

    private Map<String, Double> getPostMap(List<Post> postList) {
        Map<String, Double> postMap = new ConcurrentHashMap<>();

        postList.forEach(post -> {
            List<PostCategory> postCategories = postCategoryRepository.findAllByPost(post);

            for(PostCategory postCategory : postCategories) {
                String key = postCategory.getCategory().getCategoryName();
                Double value = postCategory.getCount();

                postMap.put(key, postMap.getOrDefault(key, 0.0) + value);
            }
        });

        return postMap;
    }

    private List<Post> getValidPostList(Member member) {
        String username = member.getUsername();
        List<Post> postList = postRepository.findAllByWriterAndVerifiable(username, true);

        if(postList.isEmpty()) throw new PostNotFoundException();

        return postList;
    }

    private List<Post> getNormalPostList(Member member) {
        String username = member.getUsername();
        List<Post> postList = postRepository.findAllByWriterAndVerifiable(username, false);

        if(postList.isEmpty()) throw new PostNotFoundException();

        return postList;
    }

    private Image getMemberImage(MultipartFile file) {
        return new MemberImage(file);
    }

    private void saveMemberImage(MultipartFile file, Image image) {
        String storedName = image.getStoredName();

        String accessUrl = imageService.saveImage(file, storedName);
        image.setAccessUrl(accessUrl);
    }
}
