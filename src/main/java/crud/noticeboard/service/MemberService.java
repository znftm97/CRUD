package crud.noticeboard.service;

import crud.noticeboard.domain.Member;

public interface MemberService {

    void createMember(Member member);
    Member findLoginMember();
}
