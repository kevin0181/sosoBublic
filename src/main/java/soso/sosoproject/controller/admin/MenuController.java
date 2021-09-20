package soso.sosoproject.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import soso.sosoproject.dto.CategoryDTO;
import soso.sosoproject.service.admin.menu.MenuService;

import java.util.List;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("add-category")
    public String addCategory(CategoryDTO categoryDTO, Model model) {
        menuService.save_menu(categoryDTO);

        //카테고리 리스트 가져오는 부분
        List<CategoryDTO> categoryList = menuService.getCategoryList();
        model.addAttribute("categoryList", categoryList);

        return "admin/add-menu";
    }

}
