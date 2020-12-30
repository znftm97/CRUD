package crud.noticeboard.controller;

import crud.noticeboard.domain.Member;
import crud.noticeboard.dto.MemberDto;
import crud.noticeboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/members/new")
    public String createMember(Model model){
        model.addAttribute("MemberDto", new MemberDto());
        return "/createMember";
    }

    @PostMapping("/members/new")
    public String create(@ModelAttribute("MemberDto") @Valid MemberDto memberDto, BindingResult result){

        if(result.hasErrors()){
            return "/createMember";
        }

        ModelMapper modelMapper = new ModelMapper();
        Member member = modelMapper.map(memberDto, Member.class);

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        memberService.createMember(member);

        return "redirect:/";
    }

}
