package soso.sosoproject.service.admin.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.CategoryDTO;
import soso.sosoproject.repository.CategoryRepository;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void save_menu(CategoryDTO categoryDTO) {
        categoryRepository.save(categoryDTO);
    }

    public List<CategoryDTO> getCategoryList() {
        List<CategoryDTO> categoryDTOList = categoryRepository.findAll();
        return categoryDTOList;
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void changeCategory(CategoryDTO categoryDTO) {
        categoryRepository.save(categoryDTO);
    }
}
