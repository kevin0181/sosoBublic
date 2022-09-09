package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.MenuCategoryDTO;

import java.util.List;

public interface CategoryRepository extends JpaRepository<MenuCategoryDTO, Long> {
    List<MenuCategoryDTO> findAllByCategoryName(String name);
}
