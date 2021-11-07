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
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.RoleDTO;
import soso.sosoproject.dto.detail.CustomOauth2Detail;
import soso.sosoproject.service.Account.MemberService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@Service
public class Oauth2UserService extends SimpleUrlAuthenticationSuccessHandler implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    HttpSession httpSession;

    @Autowired
    private MemberService memberService;


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

            MemberDTO memberDTO = memberService.findOauth2User(response);
            if (memberDTO == null) {
                return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), response, userNameAttributeName);
            } else {
//                String resultRole = "ROLE_USER";
//                List<RoleDTO> role = new ArrayList<>();
//                role.addAll(memberDTO.getRole());
//                if (role.size() == 1) {
//                    resultRole = role.get(0).getRoleName();
//                    return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(resultRole)), response, userNameAttributeName);
//                } else {
                return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), response, userNameAttributeName);
//                }
            }
        } else if (client.equals("naver")) {
            return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), response, userNameAttributeName);
        } else if (client.equals("google")) {
            return new CustomOauth2Detail(oAuth2User);
        } else if (client.equals("facebook")) {
//            return new CustomOauth2Detail(oAuth2User);
//            https로 변경후 사용가능
        }
        return null;
    }


}
