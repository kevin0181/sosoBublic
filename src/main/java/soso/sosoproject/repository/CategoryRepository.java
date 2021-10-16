package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.MenuCategoryDTO;

public interface CategoryRepository extends JpaRepository<MenuCategoryDTO, Long> {
}
