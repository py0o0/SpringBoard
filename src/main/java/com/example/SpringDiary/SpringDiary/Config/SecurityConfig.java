package com.example.SpringDiary.SpringDiary.Config;

import com.example.SpringDiary.SpringDiary.Service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth //접근 권한 설정
                        .requestMatchers("/", "/login","/loginProc","/join","/joinProc").permitAll() //퍼밋올 : 모두 접근 가능
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // 나머지는 로그인 된 유저만 접근 가능

                );

        http
                .formLogin((auth)->auth.loginPage("/login") //인증이 필요한 페이지에 접근할시 /login으로 get요청
                        .loginProcessingUrl("/loginProc") //html에서 /loginProc로 폼제출시 /loginProc에서 오는 요청이 여기서 실행
                        .failureUrl("/login?error=true") ///fail시 현 링크
                        .permitAll()
                )
                .userDetailsService(customUserDetailsService) //customUserDetailsService에서 로그인 처리 인증작업

                .logout((logout) -> logout
                .logoutUrl("/logout") // 로그아웃 요청을 받을 URL
                .logoutSuccessUrl("/") // 로그아웃 후 리다이렉트할 URL
                .invalidateHttpSession(true) // 세션 무효화
                .clearAuthentication(true) // 인증 정보 제거
                .permitAll() 
        );

        return http.build();
    }
}
