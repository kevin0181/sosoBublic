package soso.sosoproject.controller.user.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.OrderDTO;
import soso.sosoproject.service.order.OrderService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UserSosoOrderController {

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @PostMapping("/user/Reserve/calendar/order")
    public Map<String, Object> orderSoso(OrderDTO orderDTO) { //soso 주문들어온거 일단 db에 저장
        Map<String, Object> data = new HashMap<>();

        if (!orderDTO.getOrderPlace().equals("soso")) {
            data.put("error", "error7001");
            return data;
        }

        String uid = orderService.saveSosoOrder(orderDTO);

        data.put("uid", uid);
        return data;
    }
}
