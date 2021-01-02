package crud.noticeboard.controller;

import crud.noticeboard.domain.Member;
import crud.noticeboard.dto.MemberDto;
import crud.noticeboard.repository.MemberRepository;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    //회원가입 페이지 매핑
    @GetMapping("/members/new")
    public String createMember(Model model){
        model.addAttribute("MemberDto", new MemberDto());
        return "/createMember";
    }

    //회원가입 버튼 클릭
    @PostMapping("/members/new")
    public String create(@ModelAttribute("MemberDto") @Valid MemberDto memberDto, BindingResult result){

        if(result.hasErrors()){
            return "/createMember";
        }

        ModelMapper modelMapper = new ModelMapper();
        Member member = modelMapper.map(memberDto, Member.class);

        memberService.createMember(member);

        return "redirect:/";
    }

    //회원 정보 페이지 매핑
    @GetMapping("/members/info")
    public String info(Model model){
        Member findMember = memberService.findLoginMember();

        ModelMapper modelMapper = new ModelMapper();
        MemberDto memberDto = modelMapper.map(findMember, MemberDto.class);

        model.addAttribute("memberDto", memberDto);

        return "/infoMember";
    }

    //다시한번 비밀번호 검증
    @GetMapping("/members/update/valid")
    public String updateValid(Model model){

        model.addAttribute("memberDto", new MemberDto());
        return "/validUpdateMember";
    }

    //수정 페이지 매핑
    @GetMapping("/members/update")
    public String updateForm(Model model, @ModelAttribute("memberDto") MemberDto memberDto){

        Member findMember = memberService.findLoginMember();
        String findMemberPassword = findMember.getPassword();
        String reInputPassword = memberDto.getPassword();

        if (!(passwordEncoder.matches(reInputPassword, findMemberPassword))) {
            return "redirect:/members/update/valid";
        }

        ModelMapper modelMapper = new ModelMapper();
        MemberDto memberDto2 = modelMapper.map(findMember, MemberDto.class);

        model.addAttribute("memberDto", memberDto2);
        return "/updateMember";
    }

    //수정
    @PostMapping("/members/update")
    public String update(@ModelAttribute("memberDto") @Valid MemberDto memberDto, BindingResult result,
                         HttpSession session){

        if(result.hasErrors()){
            return "/updateMember";
        }
        memberService.updateMember(memberDto);

        Member findMember = memberRepository.findById(1L).get();
        boolean matches = passwordEncoder.matches("1111", findMember.getPassword());
        System.out.println("=============");
        System.out.println(matches);

        /*session.invalidate();*/


        return "/infoMember";
    }

}
