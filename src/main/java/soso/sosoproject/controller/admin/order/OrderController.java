package soso.sosoproject.controller.admin.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.message.AccountMessage;
import soso.sosoproject.service.order.PasOrderService;
import soso.sosoproject.service.order.SosoOrderService;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("admin")
public class OrderController {

    @Autowired
    private PasOrderService pasOrderService;

    @Autowired
    private SosoOrderService sosoOrderService;

    @GetMapping("/orderList")
    public String startOrderList(@RequestParam("className") String className, Model model) {

        if (className.equals("pas")) {

            //pas list get
            List<PasOrderDTO> pasOrderDTOList = pasOrderService.findAllPlaceAndEnableOrder();

            model.addAttribute("orderList", pasOrderDTOList);
            model.addAttribute("className", className);
            return "admin/Order/PasOrderList";
        } else if (className.equals("soso")) {
            //soso list get
            List<SosoOrderDTO> sosoOrderServices = sosoOrderService.findAllPlaceAndEnableOrder();
            Collections.reverse(sosoOrderServices);

            model.addAttribute("orderList", sosoOrderServices);
            model.addAttribute("className", className);
            return "admin/Order/SosoOrderList";
        }

        model.addAttribute("data", new AccountMessage("잘못된 접근입니다.(관리자에게 문의해주세요.)", "/admin/index"));
        return "/message/account-message";
    }


    @GetMapping("/order/changeDetail")
    public String changeDetailOrder(@RequestParam(value = "memberSq") String memberSq,
                                    @RequestParam(value = "uid") String uid,
                                    @RequestParam(value = "place") String place, Model model) {

        if (place.equals("pas")) {
            PasOrderDTO pasOrderDTO = pasOrderService.findOrderId(uid);
            model.addAttribute("pasOrderDTO", pasOrderDTO);
            model.addAttribute("place", "pas");
            return "/admin/Order/OrderDetailChange";
        } else if (place.equals("soso")) {
            SosoOrderDTO sosoOrderDTO = sosoOrderService.findOrderUid(uid);
            model.addAttribute("sosoOrderDTO", sosoOrderDTO);
            model.addAttribute("place", "soso");
            return "/admin/Order/OrderDetailChange";
        } else {
            return "/error/error-404-new";
        }
    }

    @ResponseBody
    @GetMapping("/orderList/sosoOrderComplte") //소소 주문 성공
    public boolean sosoOrderComplte(@RequestParam(value = "memberSq", required = false) Long memberSq,
                                    @RequestParam(value = "orders_id", required = false) Long orders_id,
                                    @RequestParam(value = "ordersMerchantUid", required = false) String ordersMerchantUid,
                                    @RequestParam(value = "ordersImpUid", required = false) String ordersImpUid) {

        boolean result = sosoOrderService.complteSosoService(memberSq, orders_id, ordersMerchantUid, ordersImpUid);

        return result;
    }

    @ResponseBody
    @GetMapping("/orderList/sosoOrderDelete") //소소 주문 취소
    public boolean sosoOrderDelete(@RequestParam(value = "memberSq", required = false) Long memberSq,
                                   @RequestParam(value = "orders_id", required = false) Long orders_id,
                                   @RequestParam(value = "ordersMerchantUid", required = false) String ordersMerchantUid,
                                   @RequestParam(value = "ordersImpUid", required = false) String ordersImpUid) {

        boolean result = sosoOrderService.deleteSosoService(memberSq, orders_id, ordersMerchantUid, ordersImpUid);

        return result;
    }

    @ResponseBody
    @GetMapping("/orderList/pasOrderComplte") //pas 주문 성공
    public boolean pasOrderComplte(@RequestParam(value = "memberSq", required = false) Long memberSq,
                                   @RequestParam(value = "orders_id", required = false) Long orders_id,
                                   @RequestParam(value = "ordersMerchantUid", required = false) String ordersMerchantUid,
                                   @RequestParam(value = "ordersImpUid", required = false) String ordersImpUid) {

        boolean result = pasOrderService.compltePasService(memberSq, orders_id, ordersMerchantUid, ordersImpUid);

        return result;
    }

    @ResponseBody
    @GetMapping("/orderList/pasOrderDelete") //pas 주문 취소
    public boolean pasOrderDelete(@RequestParam(value = "memberSq", required = false) Long memberSq,
                                  @RequestParam(value = "orders_id", required = false) Long orders_id,
                                  @RequestParam(value = "ordersMerchantUid", required = false) String ordersMerchantUid,
                                  @RequestParam(value = "ordersImpUid", required = false) String ordersImpUid) {

        boolean result = pasOrderService.deletePasService(memberSq, orders_id, ordersMerchantUid, ordersImpUid);

        return result;
    }

}
