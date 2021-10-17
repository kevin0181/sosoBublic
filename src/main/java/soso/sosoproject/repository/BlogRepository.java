package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soso.sosoproject.dto.BlogDTO;


@Repository
public interface BlogRepository extends JpaRepository<BlogDTO, Long> {
}
