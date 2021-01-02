package crud.noticeboard.service;

import crud.noticeboard.domain.Member;
import crud.noticeboard.dto.MemberDto;

public interface MemberService {

    void createMember(Member member);
    Member findLoginMember();
    Member updateMember(MemberDto memberDto);
}
