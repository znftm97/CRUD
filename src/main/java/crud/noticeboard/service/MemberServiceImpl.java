package crud.noticeboard.service;

import crud.noticeboard.domain.Member;
import crud.noticeboard.dto.MemberDto;
import crud.noticeboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원 생성
    @Transactional
    @Override
    public void createMember(Member member) {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

    //로그인한 사용자 객체 리턴
    @Override
    public Member findLoginMember() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Member findMember = memberRepository.findByName(username);

        return findMember;
    }

    //회원 정보 수정
    @Transactional
    @Override
    public Member updateMember(MemberDto memberDto) {
        Member loginMember = findLoginMember();
        loginMember.setName(memberDto.getName());
        loginMember.setEmail(memberDto.getEmail());
        loginMember.setPassword( passwordEncoder.encode(memberDto.getPassword()) );

        return loginMember;
    }
}
