package soso.sosoproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soso.sosoproject.dto.BlogDTO;

import java.util.List;


@Repository
public interface BlogRepository extends JpaRepository<BlogDTO, Long> {

    Page<BlogDTO> findAllByOrderByBlogSqDesc(Pageable pageable);

    List<BlogDTO> findAllByOrderByBlogViewSizeDesc();

}
