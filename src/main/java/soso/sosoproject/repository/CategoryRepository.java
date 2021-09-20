package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.CategoryDTO;

public interface CategoryRepository extends JpaRepository<CategoryDTO, Long> {
}
