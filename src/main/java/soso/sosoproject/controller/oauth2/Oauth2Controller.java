package soso.sosoproject.controller.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import soso.sosoproject.dto.*;
import soso.sosoproject.message.AccountMessage;
import soso.sosoproject.service.admin.menu.MenuService;
import soso.sosoproject.service.oauth2.Oauth2DataService;
import soso.sosoproject.service.user.UserBlogService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Controller
public class Oauth2Controller {

    @Autowired
    private Oauth2DataService oauth2DataService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserBlogService userBlogService;

    //oauth2 회원가입 폼으로 옴
    @GetMapping("/user/account/OAuth2form")
    public String sendForm() {
        return "user/account/OAuth2form";
    }

    //oauth2 회원가입
    @PostMapping("/user/account/signupOauth2")
    public String signUpOauth2(Oauth2DTO oauth2DTO, HttpSession session, Model model) {

        oauth2DataService.saveOauth2(oauth2DTO);

//        session.setAttribute("memberEMail", oauth2DTO.getMemberEmail());
//        session.setAttribute("memberName", oauth2DTO.getMemberName());
//
//        MemberDTO memberDTO = oauth2DataService.findOauth2Member(oauth2DTO.getMemberEmail());
//        session.setAttribute("memberSq", memberDTO.getMember_sq());
//        Iterator<RoleDTO> roleDTOInteger = memberDTO.getRole().iterator();
//        while (roleDTOInteger.hasNext()) {
//            session.setAttribute("memberRole", roleDTOInteger.next());
//        }


//        //오늘의 메뉴 가져옴
//        MenuDTO todayMenu = todayMenu();
//        model.addAttribute("todayMenu", todayMenu);
//
//        //카테고리 가져옴
//        List<MenuCategoryDTO> menuCategoryDTO = getCategory();
//        model.addAttribute("category", menuCategoryDTO);
//
//        //메뉴 리스트
//        List<MenuDTO> menuDTOList = getMenuList();
//        model.addAttribute("menu", menuDTOList);
//
//        //블로그 리스트
//        Page<BlogDTO> blogDTOPageable = userBlogService.getIndexBlogPage(0);
//        model.addAttribute("blogDTO", blogDTOPageable);
        model.addAttribute("data", new AccountMessage("회원가입이 완료되었습니다. 다시 로그인해주세요.", "/user/account/login"));
        return "/message/account-message";

    }

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
}
