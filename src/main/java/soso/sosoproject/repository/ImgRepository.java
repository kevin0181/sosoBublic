package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.ImgDTO;

import java.util.List;

public interface ImgRepository extends JpaRepository<ImgDTO, Long> {
}
