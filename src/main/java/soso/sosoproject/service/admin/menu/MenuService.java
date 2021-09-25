package soso.sosoproject.service.admin.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.CategoryDTO;
import soso.sosoproject.dto.ImgDTO;
import soso.sosoproject.dto.MenuDTO;
import soso.sosoproject.repository.CategoryRepository;
import soso.sosoproject.repository.ImgRepository;
import soso.sosoproject.repository.MenuRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ImgRepository imgRepository;

    //카테고리 생성
    public void save_category(CategoryDTO categoryDTO) {
        categoryRepository.save(categoryDTO);
    }

    //카테고리 리스트
    public List<CategoryDTO> getCategoryList() {
        List<CategoryDTO> categoryDTOList = categoryRepository.findAll();
        return categoryDTOList;
    }

    //카테고리 삭제
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    //카테고리 변경
    public void changeCategory(CategoryDTO categoryDTO) {
        Optional<CategoryDTO> getCategoryDTO = categoryRepository.findById(categoryDTO.getCategory_sq());
        categoryDTO.setMenuList(getCategoryDTO.get().getMenuList());
        categoryRepository.save(categoryDTO);
    }

    //메뉴 생성
    public List<MenuDTO> save_menu(MenuDTO menuDTO) {
        menuRepository.save(menuDTO);

        return menuRepository.findAllByMenuName(menuDTO.getMenuName());
    }

    //메뉴 리스트
    public List<MenuDTO> getMenuList() {
        List<MenuDTO> menuDTOList = menuRepository.findAll();
        return menuDTOList;
    }

    //메뉴 삭제
    public void deleteMenu(List<Long> menuCheck) throws IOException {

        String fileName;
        String imgDate = null;
        String filePath;
        String dirPath;
        List<ImgDTO> deleteImg;

        //이미지 삭제
        for (int i = 0; i < menuCheck.size(); i++) {
            deleteImg = imgRepository.findAllByMenuSq(menuCheck.get(i));
            for (int j = 0; j < deleteImg.size(); j++) {
                fileName = deleteImg.get(j).getImg_name();
                imgDate = deleteImg.get(j).getImg_date();
                filePath = "./menu-img/" + menuCheck.get(i) + "/" + imgDate + "/" + fileName;
                Path path = Paths.get(filePath);
                if (Files.exists(path)) {
                    Files.delete(path);
                }
            }

            dirPath = "./menu-img/" + menuCheck.get(i) + "/" + imgDate;
            Path deleteDirPath = Paths.get(dirPath);
            if (Files.exists(deleteDirPath)) {
                Files.delete(deleteDirPath);
            }

            imgRepository.deleteAllByMenuSq(menuCheck.get(i));
        }

        //메뉴 삭제
        for (int i = 0; i < menuCheck.size(); i++) {
            menuRepository.deleteById(menuCheck.get(i));
        }
    }

    public void changeActive(Long menu_sq, boolean active) {
        Optional<MenuDTO> optionalMenuDTO = menuRepository.findById(menu_sq);
        MenuDTO menuDTO = optionalMenuDTO.get();
        menuDTO.setMenu_enable(active);
        menuRepository.save(menuDTO);
    }

    //이미지 저장
    public void saveImg(String fileName, String fullPath, Long id, String menuName, String nowDate) {
        ImgDTO imgDTO = new ImgDTO();
        imgDTO.setImg_name(fileName);
        imgDTO.setImg_path(fullPath);
        imgDTO.setMenuSq(id);
        imgDTO.setMenuName(menuName);
        imgDTO.setImg_date(nowDate);

        imgRepository.save(imgDTO);

    }

    //이미지 저장을 위한 시퀀스 가져옴
    public List<ImgDTO> getImgList() {
        List<ImgDTO> imgDTO = imgRepository.findAll();
        return imgDTO;
    }

}
