package soso.sosoproject.controller.oauth2.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.detail.CustomOauth2Detail;
import soso.sosoproject.service.oauth2.Oauth2DataService;

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
            //카카오
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> getId = defaultOAuth2User.getAttributes();
            Map<String, Object> getEmail = (Map<String, Object>) getId.get("kakao_account");
            Map<String, Object> getName = (Map<String, Object>) getId.get("properties");
            String email = (String) getEmail.get("email");
            String name = (String) getName.get("nickname");
            String id = Integer.toString((Integer) getId.get("id"));
            String img = (String) getName.get("profile_image");

            MemberDTO memberDTO = oauth2DataService.findOauth2Member(email + id);

            if (memberDTO == null) {
                //카카오 신규회원
                session.setAttribute("email", email + id);
                session.setAttribute("name", name);
                session.setAttribute("kakaoId", id);

                response.sendRedirect("/user/account/OAuth2form");
            } else if (memberDTO != null) {
                //카카오 회원
                session.setAttribute("memberEMail", email);
                session.setAttribute("memberName", name);
                session.setAttribute("img", img);
                session.setAttribute("memberSq", memberDTO.getMember_sq());
                session.setAttribute("memberRole", (defaultOAuth2User.getAuthorities()).toString());

                response.sendRedirect("/user/index");
            }

        } else if (client.equals("naver")) {
            //네이버
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> naverMap = defaultOAuth2User.getAttributes();
            Map<String, Object> naverResponse = (Map<String, Object>) naverMap.get("response");

            String email = (String) naverResponse.get("email");
            String id = (String) naverResponse.get("id");
            String name = (String) naverResponse.get("name");
            String img = (String) naverResponse.get("profile_image");
            String mobile = (String) naverResponse.get("mobile");

            MemberDTO memberDTO = oauth2DataService.findOauth2Member(email + id);

            if (memberDTO == null) {
                //네이버 신규
                session.setAttribute("email", email + id);
                session.setAttribute("name", name);
//                session.setAttribute("mobile", mobile); 핸드폰 번호 가져오는 부분
                //회원가입 페이지로 보냄
                response.sendRedirect("/user/account/OAuth2form");
            } else if (memberDTO != null) {
                //네이버 회원
                session.setAttribute("memberEMail", email);
                session.setAttribute("memberName", name);
                session.setAttribute("img", img);
                session.setAttribute("memberSq", memberDTO.getMember_sq());
                session.setAttribute("memberRole", (defaultOAuth2User.getAuthorities()).toString());

                response.sendRedirect("/user/index");
            }

        } else if (client.equals("google")) {
            //구글
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
                session.setAttribute("memberSq", memberDTO.getMember_sq());
                session.setAttribute("memberRole", (oauth2Detail.getAuthorities()).toString());

                response.sendRedirect("/user/index");
            }
        } else if (client.equals("facebook")) {
            System.out.println("성공");
        }


        super.onAuthenticationSuccess(request, response, authentication);
    }

}
