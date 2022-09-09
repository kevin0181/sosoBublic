package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.PasOrderDTO;

import java.util.List;

public interface PasOrderRepository extends JpaRepository<PasOrderDTO, Long> {
    PasOrderDTO findByOrdersImpUid(String id);

    List<PasOrderDTO> findAllByOrdersSave(boolean active);


    List<PasOrderDTO> findAllByOrderEnableOrderByOrderDateDesc(boolean enable);
    List<PasOrderDTO> findAllByOrderEnableOrderByOrderDateAsc(boolean enable);

    PasOrderDTO findAllByOrdersMerchantUid(String id);

    String deleteByOrdersMerchantUid(String uid);

    List<PasOrderDTO> findAllByMemberSq(Long memberSq);

}
