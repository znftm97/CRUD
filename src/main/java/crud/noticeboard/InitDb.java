package crud.noticeboard;

import crud.noticeboard.domain.Member;
import crud.noticeboard.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit(){
            Member member1 = new Member();
            member1.setName("AAA");
            member1.setPassword(passwordEncoder.encode("1234"));
            member1.setEmail("AAA@naver.com");

            Member member2 = new Member();
            member2.setName("BBB");
            member2.setPassword(passwordEncoder.encode("1234"));
            member2.setEmail("BBB@naver.com");

            em.persist(member1);
            em.persist(member2);

            /*Post post1 = Post.createPost(member1, "제목제목제목1111", "내sadㅐㅁㄴ");
            em.persist(post1);
            Post post2 = Post.createPost(member1, "제목제목제목2222", "내용dsadfscxxㅐㅁㄴ");
            em.persist(post2);
            Post post3 = Post.createPost(member2, "제목제목제목3333", "내용dsadfscsajfiopsajfioxxㅐㅁㄴ");
            em.persist(post3);
            Post post4 = Post.createPost(member2, "제목제목제목4444", "내용dasd");
            em.persist(post4);*/
        }


    }
}
