package crud.noticeboard;

import crud.noticeboard.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

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
        }
    }
}
