package soso.sosoproject.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.OrderDTO;
import soso.sosoproject.repository.OrderDetailRepository;
import soso.sosoproject.repository.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public void saveOrder(OrderDTO orderDTO) {
        orderRepository.save(orderDTO);
    }

    public OrderDTO findOrderId(String ordersImpUid) {
        return orderRepository.findByOrdersImpUid(ordersImpUid);
    }

    public List<OrderDTO> findOrderNotSave() {
        return orderRepository.findAllByOrdersSaveAndOrderPlace(false, "소소한 부엌");
    }

    public List<OrderDTO> findAllPlaceOrder(String place) {
        return orderRepository.findAllByOrderPlace(place);
    }

    public List<OrderDTO> findAllPlaceAndEnableOrder(String place) {
        return orderRepository.findAllByOrderPlaceAndOrderEnableOrderByOrderDateDesc(place, false);
    }

}
