package com.example.SpringDiary.SpringDiary.Controller;

import com.example.SpringDiary.SpringDiary.Domain.Member;
import com.example.SpringDiary.SpringDiary.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

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

    @GetMapping("/admin")
    public String admin() {return "adminPage";}

    @GetMapping("/admin/userManage")
    public String adUserMag(Model model) {
        List<Member> userList = memberService.getAllUser();
        model.addAttribute("userList", userList);
        return "userManage";
    }

    @GetMapping("/admin/deleteUser/{userId}")
    public String deleteUser(@PathVariable String userId){
        memberService.deleteUser(userId);
        return "redirect:/admin/userManage";
    }
}
