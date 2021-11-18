package soso.sosoproject.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.MenuCategoryDTO;
import soso.sosoproject.repository.CategoryRepository;

import java.util.List;

@Service
public class MenuCategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<MenuCategoryDTO> findSoso() {
        return categoryRepository.findAllByCategoryName("soso");
    }
}
