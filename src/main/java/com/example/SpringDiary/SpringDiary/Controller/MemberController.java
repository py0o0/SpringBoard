package com.example.SpringDiary.SpringDiary.Controller;

import com.example.SpringDiary.SpringDiary.Domain.Member;
import com.example.SpringDiary.SpringDiary.Repository.MemberRepository;
import com.example.SpringDiary.SpringDiary.Service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String login(){
        return "loginPage";
    }

    @PostMapping("/loginProc")
    public String loginProc(){
        return "redirect:/";
    }

    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProc(Member member){
        boolean suc = memberService.joinProc(member);
        if(suc == false)
            return "redirect:/join?error=true";
        return "redirect:/join?success=true";
    }

    @GetMapping("/logout")
    void logout(){
    }

    @GetMapping("/myPage")
    public String myPage(){
        return "myPage";
    }
}
