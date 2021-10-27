package soso.sosoproject.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soso.sosoproject.dto.*;
import soso.sosoproject.service.admin.menu.MenuService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("menu")
@ResponseBody
public class MenuApiController {

    @Autowired
    private MenuService menuService;

    private List<MenuDTO> sqMenuDTO;
    private List<ImgDTO> imgDTO;

    //카테고리 추가
    @GetMapping("add-category")
    public MenuCategoryDTO addCategory(MenuCategoryDTO menuCategoryDTO, Model model) {
        menuService.save_category(menuCategoryDTO);
        //active 추가
        model.addAttribute("className", "add-menu");
        return menuCategoryDTO;
    }

    //메뉴 추가
    @PostMapping("add-menu")
    public boolean addMenu(MenuImgDTO menuImgDTO, Model model) throws IOException {

        //동일 이미지 파일 체크 부분
        if (!menuImgDTO.getMenu_img().isEmpty()) {
            if (menuImgDTO.getMenu_img().size() != menuImgDTO.getMenu_img().stream().distinct().count()) {
                throw new IOException("이미지 이름중복");
            }
        }


        //메뉴 가져와서 Entity클래스에 주입
        sqMenuDTO = menuService.save_menu(menuImgDTO);
        imgDTO = menuService.getImgList();
        int lastMenuSq = sqMenuDTO.size() - 1;

        //이미지 파일 주입 부분
        boolean checkImgfile = menuService.saveRealImg(menuImgDTO, imgDTO, sqMenuDTO, lastMenuSq, model);
        if (checkImgfile == false) {
            return true;
        }

        //active 추가
        model.addAttribute("className", "add-menu");
        return true;
    }

    //메뉴 삭제
    @GetMapping("delete-menu")
    public List<Long> deleteMenu(@RequestParam(value = "menuCheck[]") List<Long> menuCheck) throws IOException, InterruptedException {

        menuService.deleteMenu(menuCheck);
        return menuCheck;
    }

    //메뉴 변경
    @PostMapping("change-menu")
    public MenuImgDTO changeMenu(MenuImgDTO menuImgDTO, Model model) throws IOException, InterruptedException {

        //동일 이미지 파일 체크 부분
        if (!menuImgDTO.getMenu_img().isEmpty()) {
            if (menuImgDTO.getMenu_img().size() != menuImgDTO.getMenu_img().stream().distinct().count()) {
                throw new IOException("이미지 이름중복");
            }
        }

        //오늘의메뉴가 있으면 실행하지만 DB에 이미 있으면 실행을 하지 않음
        if (menuImgDTO.isMenu_today()) {
            boolean checkMenuToday = menuService.getTodayList(menuImgDTO.getMenuSq());
            if (checkMenuToday == false)
                return menuImgDTO;
        }


        //선택한 이미지 삭제
        if (menuImgDTO.getDelete_img_sq() != null)
            menuService.deleteImg(null, menuImgDTO.getDelete_img_sq());

        if (menuImgDTO != null) {
            //메뉴 가져와서 Entity클래스에 주입
            sqMenuDTO = menuService.save_menu(menuImgDTO);
            imgDTO = menuService.getImgList();
            int lastMenuSq = sqMenuDTO.size() - 1;
            //이미지 파일 주입 부분
            boolean checkImgfile = menuService.saveRealImg(menuImgDTO, imgDTO, sqMenuDTO, lastMenuSq, model);
            if (checkImgfile == false) {
                return menuImgDTO;
            }
            menuService.save_menu(menuImgDTO);
        }
        return menuImgDTO;
    }

}
