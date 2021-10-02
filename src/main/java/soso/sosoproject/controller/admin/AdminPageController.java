package soso.sosoproject.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.CategoryDTO;
import soso.sosoproject.dto.MenuDTO;
import soso.sosoproject.service.admin.menu.MenuService;

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
    public String addMenu(@RequestParam(value = "className", defaultValue = "add-menu") String className,
                          @RequestParam(value = "condition", required = false) String condition,
                          @RequestParam(value = "menu_sq", required = false) Long menu_sq, //비활성화 버튼
                          @RequestParam(value = "active", required = false) boolean active, //버튼 처리
                          @RequestParam(value = "pageId", defaultValue = "0") int pageId, //페이징 처리
                          @RequestParam(value = "searchText", required = false) String searchText, //검색 처리 (메뉴)
                          @RequestParam(value = "searchCategory", required = false) String categoryName,
                          Model model) {

        if (condition != null) {
            if (condition.equals("active")) {
                menuService.changeActive(menu_sq, active);
            }
        }

        if (searchText != null) {
            if (!searchText.equals("")) {
                List<MenuDTO> menuDTOS = menuService.getSearch(searchText);
                //검색해온 리스트
                model.addAttribute("menuList", menuDTOS);
                //카테고리 리스트 가져오는 부분
                List<CategoryDTO> categoryList = menuService.getCategoryList();
                model.addAttribute("categoryList", categoryList);

                //페이지 active 구분
                model.addAttribute("className", className);

                return "admin/add-menu";
            }
        }

        //메뉴 리스트 가져오는 부분
        Page<MenuDTO> menuList = menuService.getMenuList(pageId);

        int startSize = 1;
        int maxSize = 5;
        //5,6,7,8,9     //10,11,12,13,14
        if (pageId >= 5) {

            startSize = pageId / maxSize;
            maxSize = (startSize + 1) * maxSize;

            startSize = maxSize - 4;
        }
        if (maxSize >= menuList.getTotalPages()) {
            maxSize = menuList.getTotalPages();
        }

        model.addAttribute("menuList", menuList);
        model.addAttribute("startSzie", startSize);
        model.addAttribute("maxSize", maxSize);


        //카테고리 리스트 가져오는 부분
        List<CategoryDTO> categoryList = menuService.getCategoryList();
        model.addAttribute("categoryList", categoryList);


        //페이지 active 구분
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
