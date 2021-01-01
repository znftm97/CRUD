package crud.noticeboard.service;

import crud.noticeboard.domain.Comment;
import crud.noticeboard.domain.Member;
import crud.noticeboard.domain.Post;
import crud.noticeboard.repository.CommentRepository;
import crud.noticeboard.repository.MemberRepository;
import crud.noticeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    @Override
    public void createComment(String text, Long postId) {
        //로그인한 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Member findMember = memberRepository.findByName(username);

        Post findPost = postRepository.findByIdCustom(postId);

        Comment comment = Comment.createComment(findMember, findPost, text);

        commentRepository.save(comment);
    }

    @Transactional
    @Override
    public void readComment() {

    }
}
