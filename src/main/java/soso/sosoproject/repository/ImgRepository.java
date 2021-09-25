package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import soso.sosoproject.dto.ImgDTO;

import java.util.List;

public interface ImgRepository extends JpaRepository<ImgDTO, Long> {
    @Transactional
    Long deleteAllByMenuSq(Long id);

    List<ImgDTO> findAllByMenuSq(Long id);
}
