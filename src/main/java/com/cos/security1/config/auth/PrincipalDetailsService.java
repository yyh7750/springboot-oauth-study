package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.cos.security1.config.auth
 * fileName       : PrincipalDetailsService
 * author         : yyh77
 * date           : 2023-07-30
 * description    :
 * <p>
 * 시큐리티 설정에서 loginProcessingUrl("/login")으로 걸어놨기 때문에
 * 로그인 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는
 * loadUserByUsername 함수가 실행된다.
 * <p>
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-30        yyh77       최초 생성
 */
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();
        return new PrincipalDetails(user);
    }
}
