package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import soso.sosoproject.dto.BlogCommentDTO;

import java.util.List;


@Repository
public interface BlogCommentRepository extends JpaRepository<BlogCommentDTO, Long> {

    List<BlogCommentDTO> findAllByBlogSq(Long id);
}
