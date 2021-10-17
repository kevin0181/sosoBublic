package soso.sosoproject.controller.user.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import soso.sosoproject.dto.detail.UserDetail;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetail userDetail = (UserDetail) principal;

        session.setAttribute("memberName", userDetail.getMemberDTO().getMemberName());
        session.setAttribute("memberEMail", userDetail.getMemberDTO().getMemberEmail());
        session.setAttribute("memberSq", userDetail.getMemberDTO().getMember_sq());


        response.sendRedirect("/user/index");
    }
}
