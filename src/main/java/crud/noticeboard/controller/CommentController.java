package crud.noticeboard.controller;

import crud.noticeboard.domain.Comment;
import crud.noticeboard.domain.Member;
import crud.noticeboard.dto.CommentDto;
import crud.noticeboard.repository.CommentRepository;
import crud.noticeboard.service.CommentService;
import crud.noticeboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final CommentRepository commentRepository;

    //댓글 생성
    @PostMapping("/comment/{postId}/new")
    public String createComment(CommentDto commentDto, @PathVariable("postId") Long postId){
        commentService.createComment(commentDto.getText(), postId);
        return "redirect:/post/{postId}/read";
    }

    //댓글 수정 페이지로 매핑
    @GetMapping("/comment/{commentId}/update")
    public String updateCommentForm(@PathVariable("commentId") Long commentId, Model model){
        //검증
        Comment findComment = commentRepository.findByIdCustom(commentId);
        Member loginMember = memberService.findLoginMember();
        if ( !((loginMember.getName()).equals(findComment.getMember().getName())) ) {
            return "/error/errorUpdate";
        }

        // 기존 내용 유지
        CommentDto commentDto = new CommentDto();
        commentDto.setText(findComment.getText());

        //뷰로 전달
        model.addAttribute("commentDto", commentDto);

        return "/commnt/{commentId}/readPost";


    }

    //댓글 수정

    //댓글 삭제
    @PostMapping("/comment/{commentId}/{postId}/delete")
    public String deleteComment(@PathVariable("commentId") Long commentId, @PathVariable("postId") Long postId){
        Comment findComment = commentRepository.findByIdCustom(commentId);

        Member loginMember = memberService.findLoginMember();

        if( !((loginMember.getName()).equals(findComment.getMember().getName())) ){
            return "error/errorDelete";
        }

        commentRepository.delete(findComment);

        return "redirect:/post/{postId}/read";
    }
}
