package umc.precending.service.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.precending.domain.post.RecommendPost;
import umc.precending.domain.recommend.PersonTodayRecommend;
import umc.precending.domain.recommend.Recommend;
import umc.precending.domain.category.Category;
import umc.precending.domain.category.RecommendCategory;
import umc.precending.domain.member.Person;
import umc.precending.dto.recommend.SingleRecommendShowDto;
import umc.precending.dto.admin.RecommendCreateDto;
import umc.precending.exception.recommendGoodness.CanNotFindRecommendPost;
import umc.precending.exception.recommendGoodness.CannotChangeableRecommendException;
import umc.precending.exception.recommendGoodness.RecommendNullException;
import umc.precending.exception.recommendGoodness.RecommendSaveException;
import umc.precending.exception.category.CategoryNotFoundException;
import umc.precending.repository.category.CategoryRepository;
import umc.precending.repository.member.PersonRepository;
import umc.precending.repository.personTodayRecommendRepository.PersonTodayRecommendRepository;
import umc.precending.repository.post.RecommendPostRepository;
import umc.precending.repository.recommendRepository.RecommendRepository;
import umc.precending.service.post.PostService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class RecommendService {
    private final RecommendRepository recommendRepository;
    private final CategoryRepository categoryRepository;
    private final PersonTodayRecommendRepository personTodayRecommendRepository;
    private final PersonRepository personRepository;
    private final RecommendPostRepository recommendPostRepository;
    private final PostService postService;

    //매일 오전 8시에는 각 사용자의 추천선행이 바뀌는 로직 또한 매일 오전 8시에 남은 랜덤 추천 기회는 0이 되게 합니다.
    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void changeAllPersonRecommend(){
        personRepository.findAll().forEach(p->{
                    updateRecommendsByPerson(p);
                    p.makeNotChangeableRecommend();
        });
    }

    @Transactional
    public void changeAllPersonRecommendTest(){
        personRepository.findAll().forEach(p->{
            updateRecommendsByPerson(p);
            p.makeNotChangeableRecommend();
        });
    }

    //추천 선행을 바꿀 수 있는 상태로 변경합니다. 추후 인스타그램이나 광고 시청을 하고 이 로직을 호출하기 하면 될 듯
    @Transactional
    public void makeChangeableRecommendation(Person person){
        person.makeChangeableRecommend();
    }
//추천 선행을 바꿀수 있는 기회를 보여주는 로직
    public int showLeftRandomCount(Person person){
        if( !person.isChangeRecommend()){
            return 0;
        }
        else{
            return 1;
        }
    }
    //나의 추천선행을 변경하는 로직
    @Transactional
    public void changeMyRecommendAll(Person person){
        if(person.isChangeRecommend()){
            updateRecommendsByPerson(person);
            person.makeNotChangeableRecommend();
        }
        else{
            throw new CannotChangeableRecommendException();
        }

    }
    //기존 추천 선행을 새로운 추천 선행으로 바꿉니다
    private void updateRecommendsByPerson(Person person) {
        List<Recommend> recommends = getNewRandomRecommends(person);

        List<PersonTodayRecommend> oldRecommends=personTodayRecommendRepository.findByPersonName(person.getUsername());
        int minSize=Math.min(oldRecommends.size(),recommends.size());
        for(int i=0;i<minSize;i++){
            oldRecommends.get(i).setRecommend(recommends.get(i));
        }
        for(int i=oldRecommends.size();i<recommends.size();i++){
            person.addMyTodayRecommend(recommends.get(i));
        }
        if(recommends.size()< oldRecommends.size()){
            for(int i=recommends.size();i< oldRecommends.size();i++){
                // oldRecommends.get(i).setRecommend(null);
                personTodayRecommendRepository.deleteById(oldRecommends.get(i).getId());
            }
        }
    }
    //person 에 맞춘 새로운 Random한 Recommend들을 반환합니다
    private List<Recommend> getNewRandomRecommends(Person person) {
        Long qty=recommendRepository.countByRecommendPostAndPerson(person.getUsername());
        List<Recommend> recommends=new ArrayList<>();
        Set<Integer> selectedIndices=new HashSet<>();
        while(recommends.size()<3&&selectedIndices.size()<qty){
            int idx=(int)(Math.random()*qty);
            if(!selectedIndices.contains(idx)){
                selectedIndices.add(idx);
                List<Recommend> tmp=recommendRepository.selectRandomByPerson(person.getUsername(), PageRequest.of(idx,1));
                if(!tmp.isEmpty()){
                    recommends.add(tmp.get(0));
                }
            }
        }
        return recommends;
    }

    //사용자의 추천 선행을 하나 보여주고 사용자가 열어본 점수를 더하는 로직
   @Transactional
    public SingleRecommendShowDto recommendSingleShowDto(int num,Person person){
       List<PersonTodayRecommend> personTodayRecommends = personTodayRecommendRepository.findByPersonName(person.getUsername());
       if(personTodayRecommends.size()<=num){
           throw new RecommendNullException();
       }
      /*  if(memberTodayRecommends.get(num).getRecommend()==null){
           throw new RecommendNullException();
        }*/
       SingleRecommendShowDto singleRecommendShowDto=new SingleRecommendShowDto();
       singleRecommendShowDto.setGoodness(personTodayRecommends.get(num).getRecommend().getGoodness());
       singleRecommendShowDto.setCategories(personTodayRecommends.get(num).getRecommend().getRecommendCategories().stream().map(value->value.getCategory().getCategoryName()).collect(Collectors.toList()));
       person.addCofRc();
       return singleRecommendShowDto;
    }
    //추천 선행 만들기
    @Transactional
    public void makeRecommend(RecommendCreateDto dto){
        List<RecommendCategory> categories=getCategories(dto.getCategories());
        Recommend recommend=getRecommend(dto,categories);
        // addCategories(dto, categories);
        recommendRepository.save(recommend);
    }
    private List<RecommendCategory> getCategories(List<Long> categories){
        return categories.stream()
                .map(id->categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new))
                .map(RecommendCategory::new)
                .collect(Collectors.toList());
    }
    private Recommend getRecommend(RecommendCreateDto dto,List<RecommendCategory> categories){
        return new Recommend(dto.getContent(),categories);
    }
    private void addCategories(RecommendCreateDto dto,List<RecommendCategory> categories){
        List<Long> categoryId = dto.getCategories();

        IntStream.range(0, categoryId.size()).forEach(index -> {
            Long id = categoryId.get(index);
            RecommendCategory recommendCategory = categories.get(index);
            Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
            //setRecommendCategory(category, recommendCategory, dto);

            recommendCategory.initCategory(category);
        });
    }
    //ID로 recommend선행을 삭제합니다
    @Transactional
    public void deleteRecommendById(Long id){
        recommendRepository.deleteById(id);
    }
    //추천된 선행을 했으면 저장하는 로직.
    @Transactional
    public void RecommendSave(Person person,int num){
        List<PersonTodayRecommend> personTodayRecommends=personTodayRecommendRepository.findByPersonName(person.getUsername());
        Recommend recommend=personTodayRecommends.get(num).getRecommend();
        if(recommendPostRepository.existsByRecommendIdAndWriter(recommend.getId(),person.getUsername())){
            throw new RecommendSaveException();
        }
        RecommendPost recommendPost=new RecommendPost(personTodayRecommends.get(num));
        postService.makeRecommendPost(recommendPost);
    }

    //추천 선행 저장을 취소합니다
    @Transactional
    public void cancelSaveRecommend(Person person,int num){
        List<PersonTodayRecommend> personTodayRecommends=personTodayRecommendRepository.findByPersonName(person.getUsername());
        Recommend recommend=personTodayRecommends.get(num).getRecommend();
        if(!recommendPostRepository.existsByRecommendIdAndWriter(recommend.getId(), person.getUsername())){
            throw new CanNotFindRecommendPost();
        }
        recommendPostRepository.deleteByRecommendIdAndWriter(recommend.getId(), person.getUsername());

    }

}
