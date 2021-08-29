package soso.sosoproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.detail.UserDetail;


@Controller
public class UserPageController {

    @GetMapping("/")
    public String start(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        if (userDetail == null) {
            return "/user/index";
        }

        MemberDTO memberDTO = userDetail.getMemberDTO();
        model.addAttribute("memberDTO", memberDTO);

        return "/user/index";
    }

    @GetMapping("/user/index")
    public String index(@AuthenticationPrincipal UserDetail userDetail, Model model) {
        if (userDetail == null) {
            return "/user/index";
        }
        MemberDTO memberDTO = new MemberDTO();
        memberDTO = userDetail.getMemberDTO();
        model.addAttribute("memberDTO", memberDTO);
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
