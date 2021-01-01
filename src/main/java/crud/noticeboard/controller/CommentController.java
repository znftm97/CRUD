package crud.noticeboard.controller;

import crud.noticeboard.dto.CommentDto;
import crud.noticeboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 생성
    @PostMapping("/comment/{postId}/new")
    public String createComment(CommentDto commentDto, @PathVariable("postId") Long postId){
        commentService.createComment(commentDto.getText(), postId);
        return "redirect:/post/{postId}/read";
    }
}
