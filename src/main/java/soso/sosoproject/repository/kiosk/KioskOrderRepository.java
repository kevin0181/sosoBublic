package soso.sosoproject.repository.kiosk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soso.sosoproject.entity.kiosk.KioskOrderEntity;

import java.util.List;


@Repository
public interface KioskOrderRepository extends JpaRepository<KioskOrderEntity, Long> {


    List<KioskOrderEntity> findAllByOrderEnable(boolean id);

    KioskOrderEntity findByOrderTelegramNo(String orderTelegramNo);

    List<KioskOrderEntity> findAllByOrderDate(String date);

}
