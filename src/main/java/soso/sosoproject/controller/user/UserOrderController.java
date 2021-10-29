package soso.sosoproject.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.OrderDTO;

@Controller
public class UserOrderController {

    @PostMapping("/user/order/menu")
    @ResponseBody
    public boolean orderMenu(OrderDTO orderDTO) {

        System.out.println(orderDTO.getMember_sq());

        return true;
    }
}
