package soso.sosoproject.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.OrderDTO;
import soso.sosoproject.repository.OrderDetailRepository;
import soso.sosoproject.repository.OrderRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

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
        return orderRepository.findAllByOrdersSaveAndOrderPlace(false, "soso");
    }

    public List<OrderDTO> findAllPlaceOrder(String place) {
        return orderRepository.findAllByOrderPlace(place);
    }

    public List<OrderDTO> findAllPlaceAndEnableOrder(String place) {
        return orderRepository.findAllByOrderPlaceAndOrderEnableOrderByOrderDateDesc(place, false);
    }

    public String saveFirstOrder(OrderDTO orderDTO, int totalPay) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer(); //랜덤 난수 설정

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        sb.append(dtf.format(LocalDateTime.now()));
        int num = 0;
        do {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }

        } while (sb.length() < 20);
        String uid = sb.toString();
        orderDTO.setOrdersMerchantUid(uid);
        orderDTO.setOrdersTotalPrice(Integer.toString(totalPay));
        orderRepository.save(orderDTO);
        return uid;
    }

    public OrderDTO findUid(String ordersMerchantUid) {
        return orderRepository.findAllByOrdersMerchantUid(ordersMerchantUid);
    }

    public List<OrderDTO> findAllsosoReserveListbyDate(String date) {
        return orderRepository.findAllByOrderDateContainingAndOrderPlace(date, "soso");
    }

    public void saveSosoOrder(OrderDTO orderDTO) {

        orderRepository.save(orderDTO);

    }
}
