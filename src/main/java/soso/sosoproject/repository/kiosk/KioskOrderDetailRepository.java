package soso.sosoproject.repository.kiosk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soso.sosoproject.entity.kiosk.KioskOrderDetailEntity;
import soso.sosoproject.entity.kiosk.KioskOrderEntity;


@Repository
public interface KioskOrderDetailRepository extends JpaRepository<KioskOrderDetailEntity, Long> {

}
