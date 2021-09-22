package soso.sosoproject.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soso.sosoproject.dto.CategoryDTO;
import soso.sosoproject.dto.MenuDTO;
import soso.sosoproject.service.admin.menu.MenuService;

import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuApiController {

    @Autowired
    private MenuService menuService;
    private List<CategoryDTO> categoryList;
    private List<MenuDTO> menuList;

    //카테고리 추가
    @GetMapping("add-category")
    public CategoryDTO addCategory(CategoryDTO categoryDTO, Model model) {
        menuService.save_category(categoryDTO);
        //active 추가
        model.addAttribute("className", "add-menu");
        return categoryDTO;
    }

    //메뉴 추가
    @PostMapping("add-menu")
    public MenuDTO addMenu(MenuDTO menuDTO, Model model) {
        menuService.save_menu(menuDTO);
        //active 추가
        model.addAttribute("className", "add-menu");
        return menuDTO;
    }

    //메뉴 삭제
    @PostMapping("delete-menu")
    public List<Long> deleteMenu(@RequestParam(value = "condition", required = false) String condition,
                                 @RequestParam(value = "menuCheck[]", required = false) List<Long> menuCheck) {
        menuService.deleteMenu(menuCheck);
        return menuCheck;
    }

}
