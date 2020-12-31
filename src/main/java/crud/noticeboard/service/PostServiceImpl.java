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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;

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

    @Transactional
    @Override
    public void addCount(Post post) {
        post.addCount();
    }

    @Transactional
    @Override
    public void updatePost(Long postId, PostCreateDto postCreateDto) {
        Post findPost = postRepository.findByIdCustom(postId);
        findPost.setTitle(postCreateDto.getTitle());
        findPost.setContent(postCreateDto.getContent());
        findPost.setPostDate(LocalDateTime.now());
    }

    @Transactional
    @Override
    public void removePost(Long postId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Post findPost = postRepository.findByIdCustom(postId);

        if (username.equals(findPost.getMember().getName())){
            em.remove(findPost);
        }else{
            //에러처리 해야되는데... 음... 나중에 구현
            System.out.println("삭제에러,,");
        }

    }
}
