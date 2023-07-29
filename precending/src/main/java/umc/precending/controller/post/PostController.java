package umc.precending.controller.post;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import umc.precending.domain.member.Member;
import umc.precending.dto.post.PostEditDto;
import umc.precending.dto.post.PostNewsCreateDto;
import umc.precending.dto.post.PostNormalCreateDto;
import umc.precending.exception.member.MemberNotFoundException;
import umc.precending.repository.member.MemberRepository;
import umc.precending.response.Response;
import umc.precending.service.post.PostService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final MemberRepository memberRepository;

    @GetMapping("/posts/{year}/{month}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "게시글 전체 조회", notes = "사용자가 작성한 모든 게시글들을 조회하는 로직")
    public Response findAll(@PathVariable int year, @PathVariable int month) {
        Member findMember = getMember();
        return Response.success(postService.findAll(year, month, findMember));
    }

    @GetMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "게시글 단일 조회", notes = "게시글의 id를 통하여 특정 게시글을 조회하는 로직")
    public Response findOne(@PathVariable Long id) {
        return Response.success(postService.findOne(id));
    }

    @PostMapping("/posts/valid/news")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "인증 가능 게시글 생성 - 기사", notes = "인증 가능한 기사 게시글을 생성하는 로직")
    public void makeValidateNews(@ModelAttribute @Valid PostNewsCreateDto createDto) {
        Member findMember = getMember();
        postService.makeValidateNews(createDto, findMember);
    }

    @PostMapping("/posts/valid/post")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "인증 가능 게시글 생성 - 일반 게시글", notes = "인증 기능한 일반 게시글을 생성하는 로직")
    public void makeValidatePost(@ModelAttribute @Valid PostNormalCreateDto createDto) {
        Member findMember = getMember();
        postService.makeValidateNormalPost(createDto, findMember);
    }

    @GetMapping("/posts/valid")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "인증 가능한 게시글 조회", notes = "인증이 가능한 게시글만을 조회하는 로직")
    public Response findValidPosts() {
        return Response.success(postService.findVerifiablePost());
    }

    @PutMapping("/posts/valid/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "인증 여부 확인", notes = "인증 가능한 게시글의 유효성을 판단하는 로직")
    public void checkPostValidation(@PathVariable Long id) {
        postService.checkPostValidation(id);
    }

    @PostMapping("/posts/normal/post")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "인증 불가능한 게시글 생성 - 일반 게시글", notes = "인증할 수 없는 일반 게시글을 생성하는 로직")
    public void makeNormalPost(@ModelAttribute @Valid PostNormalCreateDto createDto) {
        Member findMember = getMember();
        postService.makeNormalPost(createDto, findMember);
    }

    @PutMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정하는 로직")
    public void editNewsPost(@ModelAttribute @Valid PostEditDto editDto, @PathVariable Long id) {
        Member findMember = getMember();
        postService.editPost(editDto, findMember, id);
    }

    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제하는 로직")
    public void deletePost(@PathVariable Long id) {
        Member findMember = getMember();
        postService.deletePost(findMember, id);
    }

    private Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return memberRepository.findMemberByUsername(username).orElseThrow(MemberNotFoundException::new);
    }
}
