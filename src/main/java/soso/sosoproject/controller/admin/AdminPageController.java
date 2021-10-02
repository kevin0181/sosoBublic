package soso.sosoproject.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.CategoryDTO;
import soso.sosoproject.dto.ImgDTO;
import soso.sosoproject.dto.MenuDTO;
import soso.sosoproject.service.admin.menu.MenuService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminPageController {

    @Autowired
    private MenuService menuService;

    //인덱스
    @GetMapping("index")
    public String index(@RequestParam(value = "className", required = false) String className, Model model) {


        //페이지 active 구분
        if (className == null) {
            className = "index";
        }
        model.addAttribute("className", className);
        return "admin/admin-index";
    }

    //로그인
    @GetMapping("login")
    public String adminLogin() {
        return "admin/admin-login";
    }

    //메뉴추가
    @GetMapping("/add-menu")
    public String addMenu(@RequestParam(value = "className", required = false) String className,
                          @RequestParam(value = "condition", required = false) String condition,
                          @RequestParam(value = "menu_sq", required = false) Long menu_sq,
                          @RequestParam(value = "active", required = false) boolean active,
                          @RequestParam(value = "pageId", defaultValue = "0") int pageId,
                          Model model) {

        if (condition != null) {
            if (condition.equals("active")) {
                menuService.changeActive(menu_sq, active);
            }
        }

        //메뉴 리스트 가져오는 부분
        Page<MenuDTO> menuList = menuService.getMenuList(pageId);
        model.addAttribute("menuList", menuList);
        model.addAttribute("maxPage",5);

        //이미지 리스트 가져옴
        List<ImgDTO> imgDTO = menuService.getImgList();
        model.addAttribute("imgList", imgDTO);

        //카테고리 리스트 가져오는 부분
        List<CategoryDTO> categoryList = menuService.getCategoryList();
        model.addAttribute("categoryList", categoryList);


        //페이지 active 구분
        if (className == null) {
            className = "add-menu";
        }
        model.addAttribute("className", className);
        return "admin/add-menu";
    }

    //카테고리 추가
    @GetMapping("/add-category")
    public String addCategory(@RequestParam(value = "className", required = false) String className,
                              @RequestParam(value = "condition", required = false) String condition,
                              @RequestParam(value = "category_id", required = false) Long categoryId,
                              @RequestParam(value = "category_name", required = false) String categoryName,
                              Model model) {


        if (condition != null) {
            if (condition.equals("change")) {
                //바뀐 카테고리 이름을 새로운 dto에 주입
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCategory_sq(categoryId);
                categoryDTO.setCategory_name(categoryName);
                menuService.changeCategory(categoryDTO);
            } else if (condition.equals("delete")) {
                menuService.deleteCategory(categoryId);
            }
        }

        //카테고리 리스트 가져오는 부분
        List<CategoryDTO> categoryList = menuService.getCategoryList();
        model.addAttribute("categoryList", categoryList);


        //페이지 active 구분
        if (className == null) {
            className = "add-category";
        }
        model.addAttribute("className", className);
        return "admin/add-category";
    }


    @GetMapping("MemberList")
    public String memberList(@RequestParam(value = "className", required = false) String className, Model model) {

        //페이지 active 구분
        if (className == null) {
            className = "MemberList";
        }
        model.addAttribute("className", className);
        return "admin/MemberList";
    }
}
