package com.cos.security1.config.auth;

import com.cos.security1.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * packageName    : com.cos.security1.config.auth
 * fileName       : PrincipalDetails
 * author         : yyh77
 * date           : 2023-07-30
 * description    :
 * <p>
 * 시큐리티가 /login을 낚아채서 로그인을 진행시켜 완료되면
 * 시큐리티 Security ContextHolder라는 키 값으로 session을 만들어준다.
 * 오브젝트 => Authentication 타입 객체
 * Authentication 안에 User 정보가 있어야 한다.
 * User 오브젝트 타입 => UserDetails 타입 객체
 * <p>
 * Security Session에 들어갈 객체는 Authentication 객체
 * Authentication 객체에는 UserDetails(PrincipalDetails)가 들어간다.
 * <p>
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-30        yyh77       최초 생성
 */
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    // 일반 로그인 시 사용되는 생성자
    public PrincipalDetails(User user) {
        this.user = user;
    }

    // OAuth 로그인 시 사용되는 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // 해당 유저의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // todo : 무슨 메소드인지
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // todo : 무슨 메소드인지
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // todo : 무슨 메소드인지
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // todo : 무슨 메소드인지
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }
}
