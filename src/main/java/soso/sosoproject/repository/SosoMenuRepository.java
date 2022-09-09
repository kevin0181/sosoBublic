package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.SosoMenuDTO;

import java.util.List;

public interface SosoMenuRepository extends JpaRepository<SosoMenuDTO, Long> {
    List<SosoMenuDTO> findAllByMenuLimit(String name);
}
