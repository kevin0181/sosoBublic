package soso.sosoproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.detail.UserDetail;
import soso.sosoproject.message.AccountMessage;

import javax.servlet.http.HttpSession;
import java.security.Principal;


@Controller
public class UserPageController {

    @GetMapping("/")
    public String start(@AuthenticationPrincipal UserDetail userDetail, Model model, Principal principal) {
        return "/user/index";
    }

    @GetMapping("/user/index")
    public String index(@AuthenticationPrincipal UserDetail userDetail, Model model, HttpSession session, MemberDTO memberDTO) {
        if (userDetail == null) {
            return "/user/index";
        }

        //회원정보 객체 가져옴 (eamil,name session에 저장)
        memberDTO = userDetail.getMemberDTO();
        session.setAttribute("memberName", memberDTO.getMemberName());
        session.setAttribute("memberEMail", memberDTO.getMemberEmail());

        return "/user/index";
    }

    @GetMapping("/user/menu")
    public String menu() {
        return "/user/menu";
    }

    @GetMapping("/user/blog")
    public String blog() {
        return "/user/blog-home";
    }

    @GetMapping("/user/about")
    public String about() {
        return "/user/about";
    }

    @GetMapping("/user/blog-single")
    public String blogSingle() {
        return "/user/blog-single";
    }

    //그냥 만들어둠 나중에 쓸꺼같아서
    @GetMapping("/user/elements")
    public String elements() {
        return "/user/elements";
    }


}
