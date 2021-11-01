package soso.sosoproject.controller.messageExample;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import soso.sosoproject.dto.OrderDTO;

@Controller
public class MessageController {

    //chat example
//    @GetMapping("/message")
//    public String msgPage() {
//        return "/user/message";
//    }

    //chat example
    @MessageMapping("/chat")
    @SendTo("/sendAdminMessage/OrderChat")
    public OrderDTO orderDTO(OrderDTO orderDTO) throws Exception { //주문시 알림처리.
        return orderDTO;
    }
}
