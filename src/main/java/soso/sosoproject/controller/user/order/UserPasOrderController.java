package soso.sosoproject.controller.user.order;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.service.admin.menu.MenuService;
import soso.sosoproject.service.order.PasOrderService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserPasOrderController {

    private IamportClient imIamportClient;

    public UserPasOrderController() {
        // REST API 키와 REST API secret 를 아래처럼 순서대로 입력한다.
        this.imIamportClient =
                new IamportClient("1152819197412694",
                        "acffbee8c37f2492f2654739c30af6863c53e981f2488325703fb8d691f222814862c2ab7d67779a");
    }

    @Autowired
    private PasOrderService pasOrderService;
    @Autowired
    private MenuService menuService;

    @PostMapping("/user/orderMenu/pay/ammount")
    @ResponseBody
    public Map<String, Object> MenuAmmount(@Valid PasOrderDTO pasOrderDTO, Errors errors) {
        Map<String, Object> data = new HashMap<>();

        if (errors.hasErrors()) { //에러 발생!
            data.put("error", true);
            return data;
        }

        int result = menuService.getOrderMenuAmmount(pasOrderDTO.getOrdersMenu());
        String uid = pasOrderService.saveFirstOrder(pasOrderDTO, result); //주문번호 콜백

        data.put("totalPrice", result);
        data.put("uid", uid);
        return data;
    }


//    @GetMapping("/user/order/menu/cancle")
//    @ResponseBody
//    public boolean cancleMenu(@RequestParam(name = "uid") String uid) {
//        try {
//            pasOrderService.cancleMenuService(uid);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
}
