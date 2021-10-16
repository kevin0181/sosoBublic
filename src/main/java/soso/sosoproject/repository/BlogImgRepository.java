package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soso.sosoproject.dto.BlogImgDTO;
import soso.sosoproject.service.Account.EmailSendService;

import java.util.List;


@Repository
public interface BlogImgRepository extends JpaRepository<BlogImgDTO, Long> {

    List<String> findByBlogImgKeyName(String key);

    List<BlogImgDTO> findAllByBlogSq(Long id);

}
