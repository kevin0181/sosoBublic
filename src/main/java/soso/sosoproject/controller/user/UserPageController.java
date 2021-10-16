package soso.sosoproject.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import soso.sosoproject.dto.MenuCategoryDTO;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.MenuDTO;
import soso.sosoproject.dto.detail.UserDetail;
import soso.sosoproject.service.admin.menu.MenuService;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


@Controller
public class UserPageController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/")
    public String start(@AuthenticationPrincipal UserDetail userDetail, Model model, Principal principal) {

        getSection(model);

        return "/user/index";
    }


    @GetMapping("/user/index")
    public String index(@AuthenticationPrincipal UserDetail userDetail, Model model, HttpSession session, MemberDTO memberDTO) {
        if (userDetail == null) {
            getSection(model);

            return "/user/index";
        }

        //회원정보 객체 가져옴 (eamil,name session에 저장)
        memberDTO = userDetail.getMemberDTO();
        session.setAttribute("memberName", memberDTO.getMemberName());
        session.setAttribute("memberEMail", memberDTO.getMemberEmail());

        getSection(model);

        return "/user/index";
    }

    @GetMapping("/user/menu")
    public String menu(Model model) {
        getSection(model);
        return "/user/menu";
    }

    @GetMapping("/user/blog")
    public String blog() {
        return "/user/blog/blog-home";
    }

    @GetMapping("/user/blog-single")
    public String blogSingle() {
        return "/user/blog/blog-single";
    }

    @GetMapping("/user/about")
    public String about() {
        return "/user/about";
    }


    //그냥 만들어둠 나중에 쓸꺼같아서
    @GetMapping("/user/elements")
    public String elements() {
        return "/user/elements";
    }


    //함수--------------------------------------------------------------------------

    //index 페이지 오늘의 메뉴 가지고 오는 함수
    public MenuDTO todayMenu() {
        MenuDTO menuDTO = menuService.getTodayMenu();
        return menuDTO;
    }

    private List<MenuCategoryDTO> getCategory() {
        //카테고리 리스트 가져오는 부분
        List<MenuCategoryDTO> categoryList = menuService.getCategoryList();
        return categoryList;
    }

    private List<MenuDTO> getMenuList() {
        return menuService.AllMenu();
    }

    private void getSection(Model model) {
        //오늘의 메뉴 가져옴
        MenuDTO todayMenu = todayMenu();
        model.addAttribute("todayMenu", todayMenu);

        //카테고리 가져옴
        List<MenuCategoryDTO> menuCategoryDTO = getCategory();
        model.addAttribute("category", menuCategoryDTO);

        //메뉴 리스트
        List<MenuDTO> menuDTOList = getMenuList();
        model.addAttribute("menu", menuDTOList);
    }


}
