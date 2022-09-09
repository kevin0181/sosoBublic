package soso.sosoproject.controller.user.order;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public Map<String, Object> orderSoso(SosoOrderDTO sosoOrderDTO) throws IOException, IamportResponseException { //soso 주문들어온거 일단 db에 저장
        Map<String, Object> data = new HashMap<>();
        try {
            IamportResponse<Payment> k = paymentByImpUid(sosoOrderDTO.getOrdersImpUid()); //가격이 같은지 검증
            String getFrontAmmount = k.getResponse().getAmount().toString();
            if (sosoOrderDTO.getOrdersTotalPrice().equals(getFrontAmmount)) {
                boolean result = sosoOrderService.specialCheckAmount(sosoOrderDTO);
                if (result) {
                    sosoOrderService.saveSosoOrder(sosoOrderDTO);
                    return data;
                } else {
                    data.put("error", "error7004"); //값 변조
                    CancelData cancelData = new CancelData(sosoOrderDTO.getOrdersImpUid(), true);
                    imIamportClient.cancelPaymentByImpUid(cancelData);
                    return data;
                }

            } else {
                data.put("error", "error7003"); //totalPrice 일치하지않으면 에러발생
                CancelData cancelData = new CancelData(sosoOrderDTO.getOrdersImpUid(), true);
                imIamportClient.cancelPaymentByImpUid(cancelData);
                return data;
            }

        } catch (Exception e) {
            data.put("error", "error7002"); //imp uid 일치하지않으면 에러발생
            CancelData cancelData = new CancelData(sosoOrderDTO.getOrdersImpUid(), true);
            imIamportClient.cancelPaymentByImpUid(cancelData);
            return data;
        }
    }

    @ResponseBody
    @PostMapping("/user/Reserve/soso/order/normal")
    public Map<String, Object> orderSosoNormal(SosoOrderDTO sosoOrderDTO) throws IOException, IamportResponseException {
        Map<String, Object> data = new HashMap<>();
        try {
            IamportResponse<Payment> k = paymentByImpUid(sosoOrderDTO.getOrdersImpUid()); //가격이 같은지 검증
            String getFrontAmmount = k.getResponse().getAmount().toString();
            if (sosoOrderDTO.getOrdersTotalPrice().equals(getFrontAmmount)) {
                boolean result = sosoOrderService.nomalCheckAmount(sosoOrderDTO);
                if (result) {

//                    String dateString = sosoOrderDTO.getOrderDate();
//                    LocalDateTime parsedLocalDateTime = LocalDateTime.parse(dateString);
//
//                    // LocalDateTime에서 필요한 내용 필요한 형식으로 뽑기
//                    String yyyyMMdd = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                    String HHmmss = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//                    String date = yyyyMMdd + " " + HHmmss;
//                    sosoOrderDTO.setOrderDate(date);

                    sosoOrderService.saveSosoOrder(sosoOrderDTO);
                    return data;
                } else {
                    data.put("error", "error7004"); //값 변조
                    CancelData cancelData = new CancelData(sosoOrderDTO.getOrdersImpUid(), true);
                    imIamportClient.cancelPaymentByImpUid(cancelData);
                    return data;
                }

            } else {
                data.put("error", "error7003"); //totalPrice 일치하지않으면 에러발생
                CancelData cancelData = new CancelData(sosoOrderDTO.getOrdersImpUid(), true);
                imIamportClient.cancelPaymentByImpUid(cancelData);
                return data;
            }

        } catch (Exception e) {

            data.put("error", "error7002"); //imp uid 일치하지않으면 에러발생
            CancelData cancelData = new CancelData(sosoOrderDTO.getOrdersImpUid(), true);
            imIamportClient.cancelPaymentByImpUid(cancelData);
            return data;

        }
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

        totalPrice = sosoOrderService.getNomalTotalPrice(menuSq, menuSize);


        return totalPrice;
    }

    @ResponseBody
    @GetMapping("/user/Reserve/soso/addInputMenuList")
    public List<SosoMenuDTO> addInputMenuRet(@RequestParam(value = "menuLimit") String menuLimit) {

        if (menuLimit.equals("기본메뉴")) {
            List<SosoMenuDTO> sosoMenuDTOList = sosoOrderService.getMenuLimitByDef(menuLimit);
            for (int i = 0; i < sosoMenuDTOList.size(); i++) {
                sosoMenuDTOList.get(i).getSosoOrdersDetailDTOS().removeAll(sosoMenuDTOList.get(i).getSosoOrdersDetailDTOS());
            }
            return sosoMenuDTOList;
        } else {
            return null;
        }

    }


    public IamportResponse<Payment> paymentByImpUid(String imp_uid) throws IamportResponseException, IOException {
        return imIamportClient.paymentByImpUid(imp_uid);
    }

}
