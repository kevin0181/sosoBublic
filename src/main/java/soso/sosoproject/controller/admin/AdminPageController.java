package soso.sosoproject.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.CategoryDTO;
import soso.sosoproject.dto.MenuDTO;
import soso.sosoproject.service.admin.menu.MenuService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminPageController {

    @Autowired
    private MenuService menuService;

    @GetMapping("index")
    public String index(@RequestParam(value = "className", required = false) String className, Model model) {


        //페이지 active 구분
        if (className == null) {
            className = "index";
        }
        model.addAttribute("className", className);
        return "admin/admin-index";
    }

    //component
    @GetMapping("component/alert")
    public String alert() {
        return "admin/component-alert";
    }

    @GetMapping("component/button")
    public String button() {
        return "admin/component-button";
    }

    @GetMapping("/component/pagination")
    public String pagination() {
        return "admin/component-pagination";
    }

    //extra-component
    @GetMapping("/extracomponent/sweetalert")
    public String sweetalert() {
        return "admin/extra-component-sweetalert";
    }

    @GetMapping("/extracomponent/toastify")
    public String toastify() {
        return "admin/extra-component-toastify";
    }


    //form
    @GetMapping("/form/input")
    public String input() {
        return "admin/form-element-input";
    }

    @GetMapping("/form/input-group")
    public String inputGroup() {
        return "admin/form-element-input-group";
    }

    @GetMapping("/form/select")
    public String select() {
        return "admin/form-element-select";
    }

    @GetMapping("/form/radio")
    public String radio() {
        return "admin/form-element-radio";
    }

    @GetMapping("/form/checkbox")
    public String checkbox() {
        return "admin/form-element-checkbox";
    }

    @GetMapping("/form/textarea")
    public String textarea() {
        return "admin/form-element-textarea";
    }

    @GetMapping("/add-menu")
    public String layout(@RequestParam(value = "className", required = false) String className,
                         @RequestParam(value = "condition", required = false) String condition,
                         @RequestParam(value = "id", required = false) Long id,
                         @RequestParam(value = "category_name", required = false) String categoryName,
                         Model model) {


        if (condition != null) {
            if (condition.equals("change")) {
                //바뀐 카테고리 이름을 새로운 dto에 주입
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCategory_sq(id);
                categoryDTO.setCategory_name(categoryName);
                menuService.changeCategory(categoryDTO);
            } else if (condition.equals("delete")) {
                menuService.deleteCategory(id);
            }
        }

        //카테고리 리스트 가져오는 부분
        List<CategoryDTO> categoryList = menuService.getCategoryList();
        model.addAttribute("categoryList", categoryList);


        //메뉴 리스트 가져오는 부분
        List<MenuDTO> menuList = menuService.getMenuList();
        model.addAttribute("menuList", menuList);

        //페이지 active 구분
        if (className == null) {
            className = "add-menu";
        }
        model.addAttribute("className", className);
        return "admin/add-menu";
    }

    @GetMapping("/form/editor")
    public String editor() {
        return "admin/form-editor-summernote";
    }


    //table
    @GetMapping("/table")
    public String table() {
        return "admin/table";
    }

    @GetMapping("/datatable")
    public String datatable() {
        return "admin/table-datatable";
    }

    //widget
    @GetMapping("/widgets/chatbox")
    public String chatbox() {
        return "admin/ui-widgets-chatbox";
    }

    @GetMapping("/widgets/todolist")
    public String toDoList() {
        return "admin/ui-widgets-todolist";
    }

    @GetMapping("/fileupload")
    public String fileUpLoad() {
        return "admin/ui-file-uploader";
    }


    //auth
    @GetMapping("/login")
    public String authLogin() {
        return "admin/admin-login";
    }

    @GetMapping("/auth/register")
    public String authRegister() {
        return "admin/auth-register";
    }

    @GetMapping("/auth/password")
    public String authPassword() {
        return "admin/auth-forgot-password";
    }
}
