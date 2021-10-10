package soso.sosoproject.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.MenuDTO;
import soso.sosoproject.dto.detail.UserDetail;
import soso.sosoproject.message.AccountMessage;
import soso.sosoproject.service.admin.menu.MenuService;

import javax.servlet.http.HttpSession;
import java.security.Principal;


@Controller
public class UserPageController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/")
    public String start(@AuthenticationPrincipal UserDetail userDetail, Model model, Principal principal) {
        //오늘의 메뉴 가져옴
        MenuDTO menuDTO = todayMenu();
        model.addAttribute("todayMenu", menuDTO);

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

        //오늘의 메뉴 가져옴
        MenuDTO menuDTO = todayMenu();
        model.addAttribute("todayMenu", menuDTO);

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


    //함수

    //index 페이지 오늘의 메뉴 가지고 오는 함수
    public MenuDTO todayMenu() {
        MenuDTO menuDTO = menuService.getTodayMenu();
        return menuDTO;
    }
}
