package umc.precending.service.recommend;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.precending.domain.Recommend.MemberTodayRecommend;
import umc.precending.domain.Recommend.Recommend;
import umc.precending.domain.category.Category;
import umc.precending.domain.category.RecommendCategory;
import umc.precending.domain.member.Member;
import umc.precending.dto.Recommend.SingleRecommendShowDto;
import umc.precending.dto.admin.RecommendCreateDto;
import umc.precending.exception.RecommendGoodness.CannotChangeableRecommendException;
import umc.precending.exception.category.CategoryNotFoundException;
import umc.precending.repository.category.CategoryRepository;
import umc.precending.repository.member.MemberRepository;
import umc.precending.repository.memberTodayRecommendRepository.MemberTodayRecommendRepository;
import umc.precending.repository.recommendRepository.RecommendRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class RecommendService {
    private final RecommendRepository recommendRepository;
    private final CategoryRepository categoryRepository;
    private final MemberTodayRecommendRepository memberTodayRecommendRepository;
    private final MemberRepository memberRepository;

    //매일 오전 8시에는 각 사용자의 추천선행이 바뀌는 로직
    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void changeAllMemberRecommend(){
        memberTodayRecommendRepository.deleteAll();
        memberRepository.findAll().forEach(m->m.setMyTodayRecommendList(recommendRepository.selectRandom()));
    }
    //매일 오전 8시에 남은 랜덤 추천 기회는 0이 되게 합니다.
    @Scheduled(cron= "0 0 8 * * *")
    @Transactional
    public void changeAllMemberToNotChangeableRecommend(){
        memberRepository.findAll().forEach(m->m.makeNotChangeableRecommend());
    }

    //추천 선행을 바꿀 수 있는 상태로 변경합니다. 추후 인스타그램이나 광고 시청을 하고 이 로직을 호출하기 하면 될 듯

    @Transactional
    public void makeChangeableRecommendation(Member member){
        member.makeChangeableRecommend();
    }
//추천 선행을 바꿀수 있는 기회를 보여주는 로직
    public int showLeftRandomCount(Member member){
        if( !member.isChangeRecommend()){
            return 0;
        }
        else{
            return 1;
        }
    }
    //나의 추천선행을 변경하는 로직
    @Transactional
    public void changeMyRecommendAll(Member member){
       /* Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username= authentication.getName();
        Member member=memberRepository.findMemberByUsername(username).get();
        List<MemberTodayRecommend> memberTodayRecommends=memberTodayRecommendRepository.findByMember_Id(member.getId());
        for (MemberTodayRecommend memberTodayRecommend : memberTodayRecommends) {
            memberTodayRecommendRepository.deleteById(memberTodayRecommend.getId());
        }
        member.setMyTodayRecommendList(recommendRepository.selectRandom());*/
        if(member.isChangeRecommend()){
            List<Recommend> recommends=recommendRepository.selectRandom();
            List<MemberTodayRecommend> oldRecommends=memberTodayRecommendRepository.findByMemberName(member.getUsername());
            for(int i=0;i<3;i++){
                oldRecommends.get(i).setRecommend(recommends.get(i));
            }
            member.makeNotChangeableRecommend();
        }
        else{
            throw new CannotChangeableRecommendException();
        }

    }

   //사용자의 추천 선행을 하나 보여주고 사용자가 열어본 점수를 더하는 로직
   @Transactional
    public SingleRecommendShowDto recommendSingleShowDto(int num,Member member){
        List<MemberTodayRecommend> memberTodayRecommends=memberTodayRecommendRepository.findByMemberName(member.getUsername());
        SingleRecommendShowDto singleRecommendShowDto=new SingleRecommendShowDto();
        singleRecommendShowDto.setGoodness(memberTodayRecommends.get(num).getRecommend().getGoodness());
        singleRecommendShowDto.setCategories(memberTodayRecommends.get(num).getRecommend().getRecommendCategories().stream().map(value->value.getCategory().getCategoryName()).collect(Collectors.toList()));
        member.addCofRc();
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



}
