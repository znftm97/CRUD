package crud.noticeboard.service;

import crud.noticeboard.domain.Member;
import crud.noticeboard.domain.Post;
import crud.noticeboard.dto.PostCreateDto;
import crud.noticeboard.repository.MemberRepository;
import crud.noticeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;

    //글 생성
    @Transactional
    @Override
    public void createPost(String title, String content) {
        //로그인한 사용자 정보 가져오기
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Member member = memberRepository.findByName(username);

        Post post = Post.createPost(member, title, content);

        postRepository.save(post);
    }

    //조회수 증가
    @Transactional
    @Override
    public void addCount(Post post) {
        post.addCount();
    }

    //글 수정
    @Transactional
    @Override
    public void updatePost(Long postId, PostCreateDto postCreateDto) {
        Post findPost = postRepository.findByIdCustom(postId);
        findPost.setTitle(postCreateDto.getTitle());
        findPost.setContent(postCreateDto.getContent());

        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm"));
        findPost.setPostDate(date);
    }

    //글 삭제
    @Transactional
    @Override
    public void removePost(Long postId) {
        Post findPost = postRepository.findByIdCustom(postId);
        em.remove(findPost);
    }


}
