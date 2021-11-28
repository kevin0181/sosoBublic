package soso.sosoproject.controller.stompController;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.MemberCountDTO;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.OrderMessageDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.service.order.PasOrderService;
import soso.sosoproject.service.order.SosoOrderService;

import java.util.ArrayList;
import java.util.List;


@Controller

public class MessageController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private PasOrderService pasOrderService;
    @Autowired
    private SosoOrderService sosoOrderService;

    private List<MemberCountDTO> memberCountDTOList = new ArrayList<>();

    boolean adminActive = false;

    boolean startPas = false;

    @Autowired
    private ObjectMapper mapper;

    //주문 stomp
    @Transactional
    @MessageMapping("/chat")
    @SendTo("/sendAdminMessage/OrderChat")
    @ResponseBody
    public Object getOrderMessage(OrderMessageDTO orderMessageDTO) throws Exception { //주문시 알림처리.

//        System.out.println("들어온 주문 이름 : " + orderMessageDTO.getOrderName());

        logger.info("들어온 주문 회원 번호 : " + orderMessageDTO.getMemberSq());
        logger.info("들어온 주문 회원 이름 : " + orderMessageDTO.getOrderName());
        logger.info("들어온 주문 주소 : " + orderMessageDTO.getOrderPlace());
        logger.info("들어온 주문 주문 번호 : " + orderMessageDTO.getOrdersImpUid());
        logger.info("들어온 주문 권한 : " + orderMessageDTO.getRole_name());
        logger.info("들어온 주문 하고싶은 말 : " + orderMessageDTO.getMessage());



        if (orderMessageDTO.getOrdersImpUid() == null) { //결제안하고 들어온 잘못된 접근
            return orderMessageDTO;
        }

        PasOrderDTO pasOrderDTO = pasOrderService.findOrderId(orderMessageDTO.getOrdersImpUid());//db에 저장된 주문한 메뉴를 가져옴

        if (adminActive) { //관리자가 로그인 중이라면?
            pasOrderDTO.setOrdersSave(true);
            pasOrderService.saveOrder(pasOrderDTO);
            orderMessageDTO.setOrderPlace("soso");

            if (startPas) { //만약 관리자가 메뉴를 받고있다면 //빠스떼우 주문
                return pasOrderDTO;
            } else { //관리자가 메뉴를 받고있지 않다면?
                orderMessageDTO.setMessage("error-404"); //관리자가 매장을 오픈하지 않음.
                return pasOrderDTO;
            }

        } else {
            pasOrderDTO.setOrdersSave(false);
            pasOrderService.saveOrder(pasOrderDTO);

            if (startPas) { //만약 관리자가 메뉴를 받고있다면 //빠스떼우 주문
                return pasOrderDTO;
            } else { //관리자가 메뉴를 받고있지 않다면?
                orderMessageDTO.setMessage("error-404"); //관리자가 매장을 오픈하지 않음.
                return pasOrderDTO;
            }

        }
    }


    @MessageMapping("/count")
    @SendTo("/sendAdminMessage/memberCount")
    public SizeAndOrderList countMember(MemberCountDTO memberCountDTO) throws Exception { //주문시 알림처리.

        logger.info("접속 이메일: " + memberCountDTO.getMemberEMail());
        logger.info("접속 사용자 번호: " + memberCountDTO.getMemberSq());
        logger.info("접속 사용자 권한: " + memberCountDTO.getRole_name());
        logger.info("접속 사용자 로그인 상태: " + memberCountDTO.isLoginActive());

        if (memberCountDTO.isLoginActive()) {
            //로그인
            if (memberCountDTO.getRole_name() == null) {
                memberCountDTOList.add(memberCountDTO);
                return new SizeAndOrderList(memberCountDTOList.size(), 0); //없으면 추가해서 리턴
            }
            if (memberCountDTO.getRole_name().equals("[ROLE_ADMIN]")) { //관리자
                //관리자 권한을 가지고 있으면 접속중인 유저 카운트를 넘김
                for (int i = 0; i < memberCountDTOList.size(); i++) { //이미 로그인 되어있는 상태
                    if (memberCountDTOList.get(i).getMemberSq() == memberCountDTO.getMemberSq()) {
                        return new SizeAndOrderList(memberCountDTOList.size(), 0);
                    }
                }
                List<SosoOrderDTO> sosoOrderDTOS = sosoOrderService.findOrderNotSave(); //소소한부엌 주문확인안된거 가져옴
                memberCountDTOList.add(memberCountDTO); //+ 어드민도 리스트에 넣음 //로그인 안되어있는 상태
                adminActive = true; //true
                return new SizeAndOrderList(memberCountDTOList.size(), sosoOrderDTOS.size());
            } else {
                //일반 유저 권한이면
                if (memberCountDTOList.size() == 0) { //리스트가 없으면 그냥 바로 추가
                    memberCountDTOList.add(memberCountDTO);
                    return new SizeAndOrderList(memberCountDTOList.size(), 0, startPas);
                } else { //리스트가 있으면?
                    for (int i = 0; i < memberCountDTOList.size(); i++) {
                        if (memberCountDTOList.get(i).getMemberSq() == memberCountDTO.getMemberSq()) { //같은 유저가 있는지 조회
                            return new SizeAndOrderList(memberCountDTOList.size(), 0, startPas); //잇으면 그냥 리턴
                        }
                    }
                    memberCountDTOList.add(memberCountDTO);
                    return new SizeAndOrderList(memberCountDTOList.size(), 0, startPas); //없으면 추가해서 리턴
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


    @MessageMapping("/start")
    @SendTo("/sendAdminMessage/startPas")
    public boolean startPas(String getPasActive) throws Exception { //주문시 알림처리.

        ObjectMapper objectMapper = new ObjectMapper();
        PasActive pasActive = objectMapper.readValue(getPasActive, PasActive.class);

        if (pasActive.isAct()) {
            startPas = true;
            return true;
        } else {
            startPas = false;
            return false;
        }
    }
}


@Getter
@Setter
class SizeAndOrderList {
    private int MemberCount;
    private int OrderSize;
    private boolean startPas;

    public SizeAndOrderList(int memberCount, int orderSize) {
        MemberCount = memberCount;
        OrderSize = orderSize;
    }

    public SizeAndOrderList(int memberCount, int orderSize, boolean startPas) {
        MemberCount = memberCount;
        OrderSize = orderSize;
        this.startPas = startPas;
    }

    public SizeAndOrderList(boolean startPas) {
        this.startPas = startPas;
    }


}

@Getter
@Setter
class PasActive {
    private boolean act;

    public PasActive(boolean act) {
        this.act = act;
    }

    public PasActive() {

    }
}