package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soso.sosoproject.dto.BlogCommentDTO;


@Repository
public interface BlogCommentRepository extends JpaRepository<BlogCommentDTO, Long> {

}
