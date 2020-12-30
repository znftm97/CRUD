package crud.noticeboard.security;

import crud.noticeboard.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MemberContext extends User {

    private final Member member;

    public Member getMember(){
        return member;
    }

    public MemberContext(Member member, Collection<? extends GrantedAuthority> authorities){
        super(member.getName(), member.getPassword(), authorities);
        this.member = member;
    }

}
