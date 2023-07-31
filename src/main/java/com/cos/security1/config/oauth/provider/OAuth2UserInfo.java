package com.cos.security1.config.oauth.provider;

/**
 * packageName    : com.cos.security1.config.oauth.provider
 * fileName       : OAuth2UserInfo
 * author         : yyh77
 * date           : 2023-07-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-31        yyh77       최초 생성
 */
public interface OAuth2UserInfo {

    String getProviderId();

    String getProvider();

    String getEmail();

    String getName();
}
