package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.OrderDTO;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDTO, Long> {
    OrderDTO findByOrdersImpUid(String id);

    List<OrderDTO> findAllByOrdersSaveAndOrderPlace(boolean active, String place);

    List<OrderDTO> findAllByOrderPlace(String place);

    //    List<OrderDTO> findAllByOrderPlaceAndOrderEnableOrderByOrderDateAsc(String place, boolean enable);
    List<OrderDTO> findAllByOrderPlaceAndOrderEnableOrderByOrderDateDesc(String place, boolean enable);


    OrderDTO findAllByOrdersMerchantUid(String id);

    List<OrderDTO> findAllByOrderDateContainingAndOrderPlace(String date, String place);

    String deleteByOrdersMerchantUid(String uid);

}
