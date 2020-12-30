package crud.noticeboard.service;

import crud.noticeboard.domain.Member;
import crud.noticeboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void createMember(Member member) {
        memberRepository.save(member);
    }
}
