package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.SosoOrdersDetailDTO;

public interface SosoOrderDetailRepository extends JpaRepository<SosoOrdersDetailDTO, Long> {
}
