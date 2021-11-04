package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.OrderDTO;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderDTO, Long> {
    OrderDTO findByOrdersImpUid(String id);

    List<OrderDTO> findAllByOrdersSave(boolean active);

    List<OrderDTO> findAllByOrderPlace(String place);
}
