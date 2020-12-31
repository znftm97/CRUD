package crud.noticeboard.service;

import crud.noticeboard.domain.Member;
import crud.noticeboard.domain.Post;
import crud.noticeboard.repository.MemberRepository;
import crud.noticeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

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
}
