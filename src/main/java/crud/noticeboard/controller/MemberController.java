package crud.noticeboard.controller;

import crud.noticeboard.domain.Member;
import crud.noticeboard.dto.MemberDto;
import crud.noticeboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/new")
    public String createMember(){
        return "/createMember";
    }

    @PostMapping("/members/new")
    public String create(MemberDto memberDto){

        System.out.println("=====================");
        ModelMapper modelMapper = new ModelMapper();
        Member member = modelMapper.map(memberDto, Member.class);

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberService.createMember(member);

        return "redirect:/";
    }

}
