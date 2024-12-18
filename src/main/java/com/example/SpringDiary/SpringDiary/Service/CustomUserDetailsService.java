package com.example.SpringDiary.SpringDiary.Service;

import com.example.SpringDiary.SpringDiary.Domain.Member;
import com.example.SpringDiary.SpringDiary.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findById(username);

        if(member == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(member.getUserId())
                .password(member.getUserPass())
                .roles(member.getRole())
                .build();
    }
}
