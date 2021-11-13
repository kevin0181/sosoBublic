package soso.sosoproject.controller.user.order;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.service.order.SosoOrderService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserSosoOrderController {

    @Autowired
    private SosoOrderService sosoOrderService;


    private IamportClient imIamportClient;

    public UserSosoOrderController() {
        // REST API 키와 REST API secret 를 아래처럼 순서대로 입력한다.
        this.imIamportClient =
                new IamportClient("1152819197412694",
                        "acffbee8c37f2492f2654739c30af6863c53e981f2488325703fb8d691f222814862c2ab7d67779a");
    }


    @ResponseBody
    @PostMapping("/user/Reserve/calendar/order")
    public Map<String, Object> orderSoso(SosoOrderDTO sosoOrderDTO) { //soso 주문들어온거 일단 db에 저장
        Map<String, Object> data = new HashMap<>();

        try {
            IamportResponse<Payment> k = paymentByImpUid(sosoOrderDTO.getOrdersImpUid()); //가격이 같은지 검증
            String getFrontAmmount = k.getResponse().getAmount().toString();
            if (sosoOrderDTO.getOrdersTotalPrice().equals(getFrontAmmount)) {
                sosoOrderService.saveSosoOrder(sosoOrderDTO);
                return data;
            } else {
                data.put("error", "error7003"); //totalPrice 일치하지않으면 에러발생
                return data;
            }

        } catch (Exception e) {

            data.put("error", "error7002"); //imp uid 일치하지않으면 에러발생
            return data;

        }

    }

    public IamportResponse<Payment> paymentByImpUid(String imp_uid) throws IamportResponseException, IOException {
        return imIamportClient.paymentByImpUid(imp_uid);
    }

}
