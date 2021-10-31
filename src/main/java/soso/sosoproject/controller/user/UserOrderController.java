package soso.sosoproject.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.OrderDTO;
import soso.sosoproject.dto.OrdersDetailDTO;
import soso.sosoproject.service.order.OrderService;

import java.util.List;

@Controller
public class UserOrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/user/order/menu")
    @ResponseBody
    public boolean orderMenu(OrderDTO orderDTO) {
        try {
            orderService.saveOrder(orderDTO);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
