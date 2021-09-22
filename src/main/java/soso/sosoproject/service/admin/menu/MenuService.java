package soso.sosoproject.service.admin.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.CategoryDTO;
import soso.sosoproject.dto.MenuDTO;
import soso.sosoproject.repository.CategoryRepository;
import soso.sosoproject.repository.MenuRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuRepository menuRepository;

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
        categoryRepository.save(categoryDTO);
    }

    //메뉴 생성
    public void save_menu(MenuDTO menuDTO) {
        menuRepository.save(menuDTO);
    }

    //메뉴 리스트
    public List<MenuDTO> getMenuList() {
        List<MenuDTO> menuDTOList = menuRepository.findAll();
        return menuDTOList;
    }

    //메뉴 삭제
    public void deleteMenu(List<Long> menuCheck) {
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
}
