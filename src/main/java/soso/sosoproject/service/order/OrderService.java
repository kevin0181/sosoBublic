package soso.sosoproject.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.OrderDTO;
import soso.sosoproject.repository.OrderDetailRepository;
import soso.sosoproject.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void saveOrder(OrderDTO orderDTO) {
        orderRepository.save(orderDTO);
    }
}
