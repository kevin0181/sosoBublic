package soso.sosoproject.controller.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.Oauth2DTO;
import soso.sosoproject.service.oauth2.Oauth2DataService;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class Oauth2Controller {

    @Autowired
    private Oauth2DataService oauth2DataService;

    //oauth2 회원가입 폼으로 옴
    @GetMapping("/user/account/OAuth2form")
    public String sendForm() {
        return "user/account/OAuth2form";
    }

    //oauth2 회원가입
    @PostMapping("/user/account/signupOauth2")
    public String signUpOauth2(Oauth2DTO oauth2DTO, HttpSession session) {

        oauth2DataService.saveOauth2(oauth2DTO);

        session.setAttribute("memberEMail", oauth2DTO.getMemberEmail());
        session.setAttribute("memberName", oauth2DTO.getMemberName());

        return "user/index";
    }

}
