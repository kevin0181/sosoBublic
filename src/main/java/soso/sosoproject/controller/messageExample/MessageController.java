package soso.sosoproject.controller.messageExample;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

    private List<MemberCountDTO> memberCountDTOList = new ArrayList<>();

    boolean adminActive;

    //주문 stomp
    @MessageMapping("/chat")
    @SendTo("/sendAdminMessage/OrderChat")
    public OrderMessageDTO getOrderMessage(OrderMessageDTO orderMessageDTO) throws Exception { //주문시 알림처리.


        //권한을 가지고 있는 유저인지 확인
        //웹소켓이 연결되었을때 관리자가 연결상태인지 아닌지 확인 (만약 연결이 안되어있으면 db로 저장)
        if (memberCountDTOList.size() != 0) { //사용자가 있는지 없는지 체크
            //관리자면 (세션?DB?ㄴㄴ 그냥 유지되므로 객체에 저장해보자) 에 저장
            for (int i = 0; i < memberCountDTOList.size(); i++) {
                if (memberCountDTOList.get(i).getRole_name().equals("[ROLE_ADMIN]")) { //접속자 중에 관리자가 있으면
                    adminActive = true; //true
                }
            }
        } else {
            adminActive = false; //false
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
            return orderMessageDTO;
        }
    }


    @MessageMapping("/count")
    @SendTo("/sendAdminMessage/memberCount")
    public SizeAndOrderList countMember(MemberCountDTO memberCountDTO) throws Exception { //주문시 알림처리.

        if (memberCountDTO.isLoginActive()) {
            //로그인
            if (memberCountDTO.getRole_name() == null) {
                memberCountDTOList.add(memberCountDTO);
                return new SizeAndOrderList(memberCountDTOList.size(), 0); //없으면 추가해서 리턴
            }
            if (memberCountDTO.getRole_name().equals("[ROLE_ADMIN]")) {
                //관리자 권한을 가지고 있으면 접속중인 유저 카운트를 넘김
                for (int i = 0; i < memberCountDTOList.size(); i++) { //이미 로그인 되어있는 상태
                    if (memberCountDTOList.get(i).getMemberSq() == memberCountDTO.getMemberSq()) {
                        return new SizeAndOrderList(memberCountDTOList.size(), 0);
                    }
                }
                List<OrderDTO> orderDTOList = orderService.findOrderNotSave();
                memberCountDTOList.add(memberCountDTO); //+ 어드민도 리스트에 넣음 //로그인 안되어있는 상태
                return new SizeAndOrderList(memberCountDTOList.size(), orderDTOList.size());
            } else {
                //일반 유저 권한이면
                if (memberCountDTOList.size() == 0) { //리스트가 없으면 그냥 바로 추가
                    memberCountDTOList.add(memberCountDTO);
                    return new SizeAndOrderList(memberCountDTOList.size(), 0);
                } else { //리스트가 있으면?
                    for (int i = 0; i < memberCountDTOList.size(); i++) {
                        if (memberCountDTOList.get(i).getMemberSq() == memberCountDTO.getMemberSq()) { //같은 유저가 있는지 조회
                            return new SizeAndOrderList(memberCountDTOList.size(), 0); //잇으면 그냥 리턴
                        }
                    }
                    memberCountDTOList.add(memberCountDTO);
                    return new SizeAndOrderList(memberCountDTOList.size(), 0); //없으면 추가해서 리턴
                }
            }
        } else {
            //로그아웃
            for (int i = 0; i < memberCountDTOList.size(); i++) {
                if (memberCountDTOList.get(i).getMemberSq() == memberCountDTO.getMemberSq()) {
                    if (memberCountDTOList.get(i).getRole_name().equals("[ROLE_ADMIN]")
                            && memberCountDTO.getRole_name().equals("[ROLE_ADMIN]")) {
                        memberCountDTOList.remove(i);
                        adminActive = false;
                    }
                    memberCountDTOList.remove(i); //해당 같은 멤버찾을때 리스트 제거
                }
            }
            return new SizeAndOrderList(memberCountDTOList.size(), 0); // 후 카운트 넘김
        }
    }
}

@Getter
@Setter
class SizeAndOrderList {
    private int MemberCount;
    private int OrderSize;

    public SizeAndOrderList(int memberCount, int orderSize) {
        MemberCount = memberCount;
        OrderSize = orderSize;
    }
}