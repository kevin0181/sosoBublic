package soso.sosoproject.controller.stompController;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import soso.sosoproject.dto.MemberCountDTO;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.OrderMessageDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.dto.kiosk.KioskMenuDTO;
import soso.sosoproject.dto.kiosk.KioskOrderDTO;
import soso.sosoproject.entity.kiosk.KioskOrderEntity;
import soso.sosoproject.service.kiosk.KioskService;
import soso.sosoproject.service.order.PasOrderService;
import soso.sosoproject.service.order.SosoOrderService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class MessageController extends ChannelInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private PasOrderService pasOrderService;
    @Autowired
    private SosoOrderService sosoOrderService;
    @Autowired
    private KioskService kioskService;


    private List<MemberCountDTO> memberCountDTOList = new ArrayList<>();
    private List<String> memberSizeList = new ArrayList<>();

    boolean adminActive = false;

    boolean startPas = false;

    boolean startKiosk = false;

    //---------------------주문------------------------------
    private IamportClient imIamportClient;


    public MessageController() {
        // REST API 키와 REST API secret 를 아래처럼 순서대로 입력한다.
        this.imIamportClient =
                new IamportClient("1152819197412694",
                        "acffbee8c37f2492f2654739c30af6863c53e981f2488325703fb8d691f222814862c2ab7d67779a");

    }


    //주문 stomp
    @Transactional
    @MessageMapping("/chat")
    @SendTo("/sendAdminMessage/OrderChat")
    @ResponseBody
    public Object getOrderMessage(OrderMessageDTO orderMessageDTO) throws Exception { //주문시 알림처리.

        if (orderMessageDTO.getOrdersImpUid() == null) { //결제안하고 들어온 잘못된 접근
            return orderMessageDTO;
        }

        PasOrderDTO pasOrderDTO = pasOrderService.findOrderId(orderMessageDTO.getOrdersImpUid());//db에 저장된 주문한 메뉴를 가져옴

        if (adminActive) { //관리자가 로그인 중이라면?
//            pasOrderDTO.setOrdersSave(true);
            pasOrderService.saveOrder(pasOrderDTO);
            orderMessageDTO.setOrderPlace("soso");

            if (startPas) { //만약 관리자가 메뉴를 받고있다면 //빠스떼우 주문
                return pasOrderDTO;
            } else { //관리자가 메뉴를 받고있지 않다면?
                orderMessageDTO.setMessage("error-404"); //관리자가 매장을 오픈하지 않음.
                return pasOrderDTO;
            }

        } else {
//            pasOrderDTO.setOrdersSave(false);
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

        if (memberCountDTO.isLoginActive()) {
            //로그인
            if (memberCountDTO.getRole_name() == null) {
                memberCountDTOList.add(memberCountDTO);
                return new SizeAndOrderList(memberSizeList.size(), 0); //없으면 추가해서 리턴
            }
            if (memberCountDTO.getRole_name().equals("[ROLE_ADMIN]")) { //관리자
                //관리자 권한을 가지고 있으면 접속중인 유저 카운트를 넘김
                for (int i = 0; i < memberCountDTOList.size(); i++) { //이미 로그인 되어있는 상태
                    if (memberCountDTOList.get(i).getMemberSq() == memberCountDTO.getMemberSq()) {
                        return new SizeAndOrderList(memberSizeList.size(), 0);
                    }
                }
                List<SosoOrderDTO> sosoOrderDTOS = sosoOrderService.findOrderNotSave(); //소소한부엌 주문확인안된거 가져옴
                memberCountDTOList.add(memberCountDTO); //+ 어드민도 리스트에 넣음 //로그인 안되어있는 상태
                adminActive = true; //true
                return new SizeAndOrderList(memberSizeList.size(), sosoOrderDTOS.size());
            } else {
                //일반 유저 권한이면
                if (memberCountDTOList.size() == 0) { //리스트가 없으면 그냥 바로 추가
                    memberCountDTOList.add(memberCountDTO);
                    return new SizeAndOrderList(memberSizeList.size(), 0, startPas);
                } else { //리스트가 있으면?
                    for (int i = 0; i < memberCountDTOList.size(); i++) {
                        if (memberCountDTOList.get(i).getMemberSq() == memberCountDTO.getMemberSq()) { //같은 유저가 있는지 조회
                            return new SizeAndOrderList(memberSizeList.size(), 0, startPas); //잇으면 그냥 리턴
                        }
                    }
                    memberCountDTOList.add(memberCountDTO);
                    return new SizeAndOrderList(memberSizeList.size(), 0, startPas); //없으면 추가해서 리턴
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
                        break;
                    }
                    memberCountDTOList.remove(i); //해당 같은 멤버찾을때 리스트 제거
                }
            }
            return new SizeAndOrderList(memberSizeList.size(), 0); // 후 카운트 넘김
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


    @MessageMapping("/start/kiosk")
    public boolean startKiosk(String getKioskActive) throws Exception { //주문시 알림처리.

        ObjectMapper objectMapper = new ObjectMapper();
        PasActive pasActive = objectMapper.readValue(getKioskActive, PasActive.class);

        if (pasActive.isAct()) {
            startKiosk = true;
            return true;
        } else {
            startKiosk = false;
            return false;
        }
    }


    //    memberSizeList
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        String commend = (accessor.getCommand().toString());
        String url = accessor.getDestination();

        if (commend.equals("SEND")) {
            if (url.equals("/order/count")) {
                memberSizeList.add(sessionId);
            }
        } else if (commend.equals("DISCONNECT")) {
            for (int i = 1; i < memberSizeList.size(); i++) {
                if (memberSizeList.get(i).equals(sessionId)) {
                    memberSizeList.remove(i);
                }
            }
        }

    }


    //------------주문---------

    @PostMapping("/user/order/menu")
    @ResponseBody
    public boolean orderMenu(PasOrderDTO pasOrderDTO) throws IOException, IamportResponseException {

        if (!startPas) { //pas 주문 닫혀있는 상태
            CancelData cancelData = new CancelData(pasOrderDTO.getOrdersImpUid(), true);
            imIamportClient.cancelPaymentByImpUid(cancelData);
            return false;
        }

        try {
            IamportResponse<Payment> k = paymentByImpUid(pasOrderDTO.getOrdersImpUid()); //가격이 같은지 검증
            String getFrontAmmount = k.getResponse().getAmount().toString();
            if (pasOrderDTO.getOrdersTotalPrice().equals(getFrontAmmount)) { //검증 통과
                boolean result = pasOrderService.checkTotalAmount(pasOrderDTO);
                if (result) {
                    pasOrderService.saveOrder(pasOrderDTO);
                    return true;
                } else {
                    CancelData cancelData = new CancelData(pasOrderDTO.getOrdersImpUid(), true);
                    imIamportClient.cancelPaymentByImpUid(cancelData);
                    return false;
                }
            } else {
                CancelData cancelData = new CancelData(pasOrderDTO.getOrdersImpUid(), true);
                imIamportClient.cancelPaymentByImpUid(cancelData);
                return false;
            }
        } catch (Exception e) {
            CancelData cancelData = new CancelData(pasOrderDTO.getOrdersImpUid(), true);
            imIamportClient.cancelPaymentByImpUid(cancelData);
            return false;
        }
    }

    @GetMapping("/admin/order/pas/orderTime")
    @ResponseBody
    public boolean getTime(@RequestParam(value = "ordersImpUid") String ordersImpUid, @RequestParam(value = "time") String time) {

        pasOrderService.saveTime(ordersImpUid, time);
        PasOrderDTO pasOrderDTO = pasOrderService.getOrderImpUid(ordersImpUid); //주문 가져옴

//        return pasOrderDTO.getMember_sq().getMemberDeviceDTO().getDeviceNumber(); //주문 당사자 디바이스 토큰값 가져옴
        return true;
    }


    public IamportResponse<Payment> paymentByImpUid(String imp_uid) throws IamportResponseException, IOException {
        return imIamportClient.paymentByImpUid(imp_uid);
    }


    //--------------------------키오스크 ------------------------------------------------


    @MessageMapping("/kiosk")
    @SendTo("/sendAdminMessage/kiosk/order")
    @ResponseBody
    public Object GetKioskOrder(@RequestBody Map<String, Object> data) throws Exception { //주문시 알림처리.

        if (startKiosk == false) {
            return new soso.sosoproject.dto.kiosk.Message("noStart");
        } else {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<KioskMenuDTO> kioskMenuDTOList = mapper.convertValue(data.get("orderMenu"), new TypeReference<List<KioskMenuDTO>>() {
                });

                KioskOrderDTO kioskOrderDTO = mapper.convertValue(data.get("orderData"), new TypeReference<KioskOrderDTO>() {
                });

                String orderNumber = (String) data.get("orderNumber");

                kioskOrderDTO.setOrderNumber(orderNumber); //주문 번호

                KioskOrderEntity kioskOrderEntity = kioskService.orderSave(kioskMenuDTOList, kioskOrderDTO);

                return kioskOrderEntity;
            } catch (Exception e) {

                e.printStackTrace();

                return new soso.sosoproject.dto.kiosk.Message("error");

            }
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