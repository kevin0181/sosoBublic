package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.OrderDTO;

public interface OrderRepository extends JpaRepository<OrderDTO, Long> {
    OrderDTO findByOrdersImpUid(String id);
}
