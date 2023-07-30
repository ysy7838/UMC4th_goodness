package umc.precending.service.recommend;

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


    @Scheduled(cron = "0 0 8 * * *")
    @Transactional
    public void changeAllMemberRecommend(){
        memberTodayRecommendRepository.deleteAll();
        memberRepository.findAll().forEach(m->m.setMyTodayRecommendList(recommendRepository.selectRandom()));
    }

    @Transactional
    public void makeChangeableRecommendation(Member member){
        member.makeChangeableRecommend();
    }

    public int showLeftRandomCount(Member member){
        if( !member.isChangeRecommend()){
            return 0;
        }
        else{
            return 1;
        }
    }

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


   @Transactional
    public SingleRecommendShowDto recommendSingleShowDto(int num,Member member){
        List<MemberTodayRecommend> memberTodayRecommends=memberTodayRecommendRepository.findByMemberName(member.getUsername());
        SingleRecommendShowDto singleRecommendShowDto=new SingleRecommendShowDto();
        singleRecommendShowDto.setGoodness(memberTodayRecommends.get(num).getRecommend().getGoodness());
        singleRecommendShowDto.setCategories(memberTodayRecommends.get(num).getRecommend().getRecommendCategories().stream().map(value->value.getCategory().getCategoryName()).collect(Collectors.toList()));
        member.addCofRc();
        return singleRecommendShowDto;
    }

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
}
