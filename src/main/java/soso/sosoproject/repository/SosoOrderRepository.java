package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.SosoOrderDTO;

import java.util.List;

public interface SosoOrderRepository extends JpaRepository<SosoOrderDTO, Long> {
    //    List<SosoOrderDTO> findAllByOrderEnableOrderByOrderDateDesc(boolean b);
    List<SosoOrderDTO> findAllByOrderEnable(boolean b);

    List<SosoOrderDTO> findAllByOrdersSave(boolean result);

    SosoOrderDTO findByOrdersImpUid(String uid);
}
