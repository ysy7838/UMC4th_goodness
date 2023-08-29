package umc.precending.service.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import umc.precending.domain.image.Image;
import umc.precending.domain.image.MemberImage;
import umc.precending.domain.member.Member;
import umc.precending.dto.person.MemberUpdateRequestDto;
import umc.precending.repository.member.MemberRepository;
import umc.precending.service.image.ImageService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final ImageService imageService;
    private final MemberRepository memberRepository;

    // 회원의 프로필을 설정하는 로직
    @Transactional
    public void saveImage(MultipartFile file, Member member) {
        Image image = getMemberImage(file);
        saveMemberImage(file, image);

        member.saveImage(List.of((MemberImage)image));
    }

    public Image getMemberImage(MultipartFile file) {
        return new MemberImage(file);
    }

    public void saveMemberImage(MultipartFile file, Image image) {
        String storedName = image.getStoredName();

        String accessUrl = imageService.saveImage(file, storedName);
        image.setAccessUrl(accessUrl);
    }

    // 회원정보 수정 로직
    @Transactional
    public void updateMember(Member member, MemberUpdateRequestDto request) {
        member.update(request);
        memberRepository.save(member);
    }

    // 회원탈퇴
    @Transactional
    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }
}
