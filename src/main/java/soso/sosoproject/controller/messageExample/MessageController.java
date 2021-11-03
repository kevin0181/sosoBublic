package soso.sosoproject.controller.messageExample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import soso.sosoproject.dto.MemberCountDTO;
import soso.sosoproject.dto.OrderDTO;
import soso.sosoproject.dto.OrderMessageDTO;
import soso.sosoproject.service.order.OrderService;

import java.util.ArrayList;
import java.util.List;


@Controller
public class MessageController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private SessionRegistry sessionRegistry;

    private List<MemberCountDTO> memberCountDTOList = new ArrayList<>();

    //chat example
    @MessageMapping("/chat")
    @SendTo("/sendAdminMessage/OrderChat")
    public OrderMessageDTO getOrderMessage(OrderMessageDTO orderMessageDTO) throws Exception { //주문시 알림처리.

        boolean adminActive;
        //권한을 가지고 있는 유저인지 확인
        //웹소켓이 연결되었을때 관리자가 연결상태인지 아닌지 확인 (만약 연결이 안되어있으면 db로 저장)
        if (sessionRegistry.getAllPrincipals().size() != 0) { //관리자 권한 들고있음
            //관리자면 (세션?DB?ㄴㄴ 그냥 유지되므로 객체에 저장해보자) 에 저장
            adminActive = true;
        } else {
            adminActive = false;
        }


        if (adminActive) { //만약 관리자가 로그인중이라면
            //db에 저장된 주문한 메뉴를 가져옴
            OrderDTO orderDTO = orderService.findOrderId(orderMessageDTO.getOrdersImpUid());
            orderDTO.setOrdersSave(true);   //저장상태 true로 저장함
            orderService.saveOrder(orderDTO);
            return orderMessageDTO;
        } else { //관리자가 로그인중 아니면
            OrderDTO orderDTO = orderService.findOrderId(orderMessageDTO.getOrdersImpUid());
            orderDTO.setOrdersSave(false);
            orderService.saveOrder(orderDTO); //저장상태 false로 저장함. 나중에 관리자가 들어오면 한번에 메시지 가져올 수 있게.
            return null;
        }
    }


    @MessageMapping("/count")
    @SendTo("/sendAdminMessage/memberCount")
    public int countMember(MemberCountDTO memberCountDTO) throws Exception { //주문시 알림처리.

        if (memberCountDTO.getRole_name().equals("[ROLE_ADMIN]")) {
            for (int i = 0; i < memberCountDTOList.size(); i++) {
                if (memberCountDTOList.get(i).getMemberSq() == memberCountDTO.getMemberSq()) {
                    return memberCountDTOList.size();
                }
            }
            memberCountDTOList.add(memberCountDTO);
            return memberCountDTOList.size();
        } else {
            if (memberCountDTOList.size() == 0) {
                memberCountDTOList.add(memberCountDTO);
                return memberCountDTOList.size();
            } else {
                for (int i = 0; i < memberCountDTOList.size(); i++) {
                    if (memberCountDTOList.get(i).getMemberSq() == memberCountDTO.getMemberSq()) {
                        return memberCountDTOList.size();
                    }
                }
                memberCountDTOList.add(memberCountDTO);
                return memberCountDTOList.size();
            }
        }
    }
}
