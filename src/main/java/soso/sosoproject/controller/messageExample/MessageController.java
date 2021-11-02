package soso.sosoproject.controller.messageExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import soso.sosoproject.dto.OrderDTO;
import soso.sosoproject.dto.OrderMessageDTO;
import soso.sosoproject.service.order.OrderService;

import javax.servlet.http.HttpSession;


@Controller
public class MessageController {

    @Autowired
    private HttpSession session;

    @Autowired
    private OrderService orderService;

    //chat example
    @MessageMapping("/chat")
    @SendTo("/sendAdminMessage/OrderChat")
    public OrderDTO getOrderMessage(OrderMessageDTO orderMessageDTO) throws Exception { //주문시 알림처리.
        //권한을 가지고 있는 유저인지 확인
        //웹소켓이 연결되었을때 관리자가 연결상태인지 아닌지 확인 (만약 연결이 안되어있으면 db로 저장)
        if (orderMessageDTO.getRole_name().equals("[ROLE_ADMIN]")) { //관리자 권한 들고있음
            //관리자면 세션에 저장
            session.setAttribute("AdminLoginActive", true); //websocket disconnect 할때 false로 바꿔줘야함 관리자.
        }


        boolean adminActive = false;
        if (session.getAttribute("AdminLoginActive") != null) { //빈값인지 아닌지 체크.
            adminActive = (boolean) session.getAttribute("AdminLoginActive"); //관리자 상태 가져옴
        }

        if (adminActive) { //만약 관리자가 로그인중이라면
            //db에 저장된 주문한 메뉴를 가져옴
            OrderDTO orderDTO = orderService.findOrderId(orderMessageDTO.getOrdersImpUid());
            return orderDTO;
        } else { //관리자가 로그인중 아니면
            OrderDTO orderDTO = orderService.findOrderId(orderMessageDTO.getOrdersImpUid());
            orderDTO.setOrdersSave(false);
            orderService.saveOrder(orderDTO); //저장상태 false로 저장함. 나중에 관리자가 들어오면 한번에 메시지 가져올 수 있게.
            return null;
        }


    }

}
