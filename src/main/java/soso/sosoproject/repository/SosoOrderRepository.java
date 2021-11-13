package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.SosoOrderDTO;

public interface SosoOrderRepository extends JpaRepository<SosoOrderDTO, Long> {
}
