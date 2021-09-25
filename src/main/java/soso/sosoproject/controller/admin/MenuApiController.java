package soso.sosoproject.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import soso.sosoproject.dto.CategoryDTO;
import soso.sosoproject.dto.ImgDTO;
import soso.sosoproject.dto.MenuDTO;
import soso.sosoproject.dto.MenuImgDTO;
import soso.sosoproject.service.admin.menu.MenuService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuApiController {

    @Autowired
    private MenuService menuService;

    //카테고리 추가
    @GetMapping("add-category")
    public CategoryDTO addCategory(CategoryDTO categoryDTO, Model model) {
        menuService.save_category(categoryDTO);
        //active 추가
        model.addAttribute("className", "add-menu");
        return categoryDTO;
    }

    //메뉴 추가, 변경
    @PostMapping("add-menu")
    public MenuImgDTO addMenu(MenuImgDTO menuImgDTO, Model model) throws IOException {

        //동일 이미지 파일 체크 부분
        if (!menuImgDTO.getMenu_img().isEmpty()) {
            if (menuImgDTO.getMenu_img().size() != menuImgDTO.getMenu_img().stream().distinct().count()) {
                throw new IOException("이미지 이름중복");
            }
        }

        List<MenuDTO> sqMenuDTO;
        String filePath;

        //메뉴 가져와서 Entity클래스에 주입
        MenuDTO menuDTO = new MenuDTO(menuImgDTO.getMenu_sq(), menuImgDTO.getMenu_name(), menuImgDTO.getMenu_category_sq(), menuImgDTO.getMenu_contant(),
                menuImgDTO.getMenu_price(), menuImgDTO.isMenu_sold_out(), menuImgDTO.isMenu_enable(), menuImgDTO.isMenu_today());
        sqMenuDTO = menuService.save_menu(menuDTO);
        List<ImgDTO> imgDTO = menuService.getImgList();

        int lastMenuSq = sqMenuDTO.size() - 1;


        //이미지 파일 주입 부분
        if (!menuImgDTO.getMenu_img().isEmpty()) {
            for (int i = 0; i < menuImgDTO.getMenu_img().size(); i++) {
                String fileName = StringUtils.cleanPath(menuImgDTO.getMenu_img().get(i).getOriginalFilename());

                // 현재 날짜 구하기
                Date now = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                String nowDate = format.format(now);
                if (imgDTO.isEmpty()) {
                    filePath = "./menu-img/" + nowDate + "/" + sqMenuDTO.get(lastMenuSq).getMenuSq();
                } else {
                    filePath = "./menu-img/" + nowDate + "/" + sqMenuDTO.get(lastMenuSq).getMenuSq();
                }
                Path path = Paths.get(filePath);

                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }


                try {
                    InputStream inputStream = menuImgDTO.getMenu_img().get(i).getInputStream();
                    Path pushFilePath = path.resolve(fileName);
                    Files.copy(inputStream, pushFilePath, StandardCopyOption.REPLACE_EXISTING);
                    menuService.saveImg(fileName, pushFilePath.toString(), sqMenuDTO.get(lastMenuSq).getMenuSq(), sqMenuDTO.get(lastMenuSq).getMenuName(), nowDate);
                } catch (IOException e) {
                    throw new IOException("파일업로드 안됌");
                }

            }
        }


        //active 추가
        model.addAttribute("className", "add-menu");
        return menuImgDTO;
    }

    //메뉴 삭제
    @PostMapping("delete-menu")
    public List<Long> deleteMenu(@RequestParam(value = "condition", required = false) String condition,
                                 @RequestParam(value = "menuCheck[]", required = false) List<Long> menuCheck) {
        menuService.deleteMenu(menuCheck);
        return menuCheck;
    }

}
