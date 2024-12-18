package com.example.SpringDiary.SpringDiary.Service;

import com.example.SpringDiary.SpringDiary.Domain.Member;
import com.example.SpringDiary.SpringDiary.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    public boolean joinProc(Member member) {
        int suc = memberRepository.isUser(member);
        if(suc!=0) return false;

        member.setRole("USER");
        member.setUserPass(bCryptPasswordEncoder.encode(member.getUserPass())); //비밀번호 해싱
        memberRepository.joinProc(member);
        return true;
    }
}
