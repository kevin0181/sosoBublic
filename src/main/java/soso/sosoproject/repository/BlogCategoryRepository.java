package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soso.sosoproject.dto.BlogCategoryDTO;

import java.util.List;


@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategoryDTO, Long> {

}
