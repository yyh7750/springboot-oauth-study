package com.cos.security1.controller;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * packageName    : com.cos.security1.controller
 * fileName       : IndexController
 * author         : yyh77
 * date           : 2023-07-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-30        yyh77       최초 생성
 */
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * UserDetails -> 일반 로그인
     * OAuth2User -> OAuth 로그인
     *
     * 세션 안에는 시큐리티 세션이 있다.
     * 시큐리티 세션 안에 들어가는 타입은 Authentication 밖에 없다.
     *
     * Authentication 객체 안에 들어가는 타입이 UserDetails, OAuth2User 두 가지이다.
     *
     * 컨트롤러에서 두 가지를 한번에 받기 위해 PrincipalDetails에서 UserDetails, OAuth2User를 모두 implements 한다.
     * -> @AuthenticationPrincipal PrincipalDetails principalDetails


    @GetMapping("/test/login")
    public @ResponseBody String loginTest(
            Authentication authentication,
            @AuthenticationPrincipal PrincipalDetails userDetails
    ) {

        // 다운 캐스팅 방법
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication : " + principalDetails.getUser());

        // @AuthenticationPrincipal DI를 이용한 방법
        System.out.println("authentication : " + userDetails.getUser());

        // 두 방법 모두 같은 결과다

        return "일반 로그인 세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String loginOauthTest(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oAuth2
    ) {

        // 다운 캐스팅 방법
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication : " + oAuth2User.getAttributes());

        // @AuthenticationPrincipal DI를 이용한 방법
        System.out.println("authentication : " + oAuth2.getAttributes());

        // 두 방법 모두 같은 결과다

        return "OAuth2 로그인 세션 정보 확인하기";
    }

     */

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(principalDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {

        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);

        return "redirect:/loginForm";
    }

    @GetMapping("/info")
    @Secured("ROLE_ADMIN")
    public @ResponseBody String info() {
        return "개인정보";
    }

    @GetMapping("/data")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // @PostAuthorize도 있다.
    public @ResponseBody String data() {
        return "데이터 정보";
    }
}
