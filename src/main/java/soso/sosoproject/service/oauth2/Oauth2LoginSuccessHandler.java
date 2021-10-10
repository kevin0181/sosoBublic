package soso.sosoproject.service.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.detail.CustomOauth2Detail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private Oauth2DataService oauth2DataService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String client = (String) session.getAttribute("client");

        if (client.equals("kakao")) {

            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> getId = defaultOAuth2User.getAttributes();
            Map<String, Object> getEmail = (Map<String, Object>) getId.get("kakao_account");
            Map<String, Object> getName = (Map<String, Object>) getId.get("properties");
            String email = (String) getEmail.get("email");
            String name = (String) getName.get("nickname");
            String id = Integer.toString((Integer) getId.get("id"));

            MemberDTO memberDTO = oauth2DataService.findOauth2Member(email + id);

            if (memberDTO == null) {
                //카카오 신규회원
                session.setAttribute("email", email + id);
                session.setAttribute("name", name);

                response.sendRedirect("/user/account/OAuth2form");
            } else if (memberDTO != null) {
                //카카오 회원
                session.setAttribute("memberEMail", email);
                session.setAttribute("memberName", name);

                response.sendRedirect("/user/index");
            }

        } else if (client.equals("google")) {
            CustomOauth2Detail oauth2Detail = (CustomOauth2Detail) authentication.getPrincipal();
            String email = oauth2Detail.getEmail();
            MemberDTO memberDTO = oauth2DataService.findOauth2Member(email + oauth2Detail.getAttribute("sub"));
            if (memberDTO == null) {
                //구글 신규회원
                session.setAttribute("email", email + oauth2Detail.getAttributes().get("sub"));
                session.setAttribute("name", oauth2Detail.getName());
                //회원가입 페이지로 보냄
                response.sendRedirect("/user/account/OAuth2form");
            } else if (memberDTO != null) {
                //구글 회원
                session.setAttribute("memberEMail", email);
                session.setAttribute("memberName", oauth2Detail.getName());

                response.sendRedirect("/user/index");
            }
        }


        super.onAuthenticationSuccess(request, response, authentication);
    }

}
