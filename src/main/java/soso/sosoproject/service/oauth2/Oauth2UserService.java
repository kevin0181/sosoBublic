package soso.sosoproject.service.oauth2;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.detail.CustomOauth2Detail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@Service
public class Oauth2UserService extends SimpleUrlAuthenticationSuccessHandler implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    HttpSession httpSession;


    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        //서비스 구분작업
        String client = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> response = oAuth2User.getAttributes();
        httpSession.setAttribute("client", client);
        if (client.equals("kakao")) {
//            Map<String, Object> kakaoEmail = (Map<String, Object>) response.get("kakao_account");
//            Map<String, Object> kakaoName = (Map<String, Object>) response.get("properties");

            return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), response, userNameAttributeName);
        } else if (client.equals("google")) {
            return new CustomOauth2Detail(oAuth2User);
        }
        return null;
    }

}
