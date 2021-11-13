package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.SosoMenuDTO;

public interface SosoMenuRepository extends JpaRepository<SosoMenuDTO, Long> {
}
