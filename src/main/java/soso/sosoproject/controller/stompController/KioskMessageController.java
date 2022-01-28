package soso.sosoproject.controller.stompController;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.kiosk.PracMessage;

@Controller
public class KioskMessageController extends ChannelInterceptorAdapter {

    @MessageMapping("/kiosk")
    @SendTo("/sendAdminMessage/kiosk/order")
//    @ResponseBody
    public PracMessage prac(PracMessage pracMessage) throws Exception { //주문시 알림처리.
        System.out.println(pracMessage.getMessage());
        return pracMessage;
    }

}
