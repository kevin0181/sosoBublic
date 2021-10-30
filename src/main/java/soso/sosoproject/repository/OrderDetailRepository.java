package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.OrdersDetailDTO;

public interface OrderDetailRepository extends JpaRepository<OrdersDetailDTO, Long> {
}
