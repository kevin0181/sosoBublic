package soso.sosoproject.controller.user.order;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.SosoMenu;
import soso.sosoproject.dto.SosoMenuDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.service.order.SosoOrderService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    @PostMapping("/user/Reserve/soso/order")
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

    @ResponseBody
    @PostMapping("/user/Reserve/soso/order/normal")
    public Map<String, Object> orderSosoNormal(SosoOrderDTO sosoOrderDTO) {
        Map<String, Object> data = new HashMap<>();

//        try {
        sosoOrderService.saveSosoOrder(sosoOrderDTO);
        return data;
//        } catch (Exception e) {
//            data.put("error", "error7005"); //알수없는 오류
//            return data;
//        }
    }


    @ResponseBody
    @GetMapping("/user/Reserve/soso/totalPrice")
    public int getTotalPrice(@RequestParam(value = "menu_order_sq") Long menu_order_sq,
                             @RequestParam(value = "userSize") int userSize) {

        int totalPrice = 0;

        totalPrice = sosoOrderService.getTotalService(menu_order_sq, userSize);

        return totalPrice;
    }

    @ResponseBody
    @GetMapping("/user/Reserve/soso/nomal/totalPrice")
    public int getNomalTotalPrice(@RequestParam(value = "menuSq[]") List<Long> menuSq,
                                  @RequestParam(value = "menuSize[]") List<Integer> menuSize) {
        int totalPrice = 0;

        if (menuSq.size() != menuSize.size()) {
            return 0;
        }

        totalPrice = sosoOrderService.getNomalTotalPrice(menuSq,menuSize);


        return totalPrice;
    }

    @ResponseBody
    @GetMapping("/user/Reserve/soso/addInputMenuList")
    public List<SosoMenuDTO> addInputMenuRet(@RequestParam(value = "menuLimit") String menuLimit) {

        if (menuLimit.equals("기본메뉴")) {
            List<SosoMenuDTO> sosoMenuDTOList = sosoOrderService.getMenuLimitByDef(menuLimit);
            return sosoMenuDTOList;
        } else {
            return null;
        }

    }


    public IamportResponse<Payment> paymentByImpUid(String imp_uid) throws IamportResponseException, IOException {
        return imIamportClient.paymentByImpUid(imp_uid);
    }

}
