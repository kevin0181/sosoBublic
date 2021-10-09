package soso.sosoproject.service.oauth2;

import lombok.SneakyThrows;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.detail.CustomOauth2Detail;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;


@Service
public class Oauth2UserService extends DefaultOAuth2UserService {

    private final HttpSession httpSession;

    public Oauth2UserService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @SneakyThrows
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);


        String client = userRequest.getClientRegistration().getRegistrationId();

        if (client.equals("kakao")) {
//            Map<String, Object> response = user.getAttributes();
//            Map<String, Object> name = (Map<String, Object>) response.get("properties");
//            Map<String, Object> email = (Map<String, Object>) response.get("kakao_account");
            OAuth2User oAuth2User = super.loadUser(userRequest);
            Map<String, Object> attributes = oAuth2User.getAttributes();

            httpSession.setAttribute("login_info", attributes);

            return new DefaultOAuth2User( Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "id" );

        }
        return new CustomOauth2Detail(user);
    }
}
