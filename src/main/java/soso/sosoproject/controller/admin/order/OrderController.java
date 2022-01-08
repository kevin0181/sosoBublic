package soso.sosoproject.controller.admin.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.message.AccountMessage;
import soso.sosoproject.service.order.PasOrderService;
import soso.sosoproject.service.order.SosoOrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public String changeDetailOrderGoPage(@RequestParam(value = "memberSq") String memberSq,
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

    @PostMapping("/detail/order/change/soso")
    public String sosoChangeDetailOrder(SosoOrderDTO sosoOrderDTO, Model model) throws ParseException {

        String dateString = sosoOrderDTO.getOrderDate();
        LocalDateTime parsedLocalDateTime = LocalDateTime.parse(dateString);

        // LocalDateTime에서 필요한 내용 필요한 형식으로 뽑기
        String yyyyMMdd = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String HHmmss = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String date = yyyyMMdd + " " + HHmmss;

        Optional<SosoOrderDTO> sosoOrderDTOGetId = sosoOrderService.findAllById(sosoOrderDTO);

        sosoOrderDTOGetId.get().setOrderDate(date);

        sosoOrderService.saveSosoOrder(sosoOrderDTOGetId.get());

        model.addAttribute("data", new AccountMessage("수정되었습니다.", "/admin/orderList?className=soso"));
        return "/message/account-message";
    }

    @PostMapping("/detail/order/change/pas")
    public String pasChangeDetailOrder(PasOrderDTO pasOrderDTO, Model model) throws ParseException {

        model.addAttribute("data", new AccountMessage("수정되었습니다.", "/admin/orderList?className=soso"));
        return "/message/account-message";
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

    @ResponseBody
    @GetMapping("/order/delete-order/pas") //pas 주문 내역 삭제
    public boolean deletePasOrderList(@RequestParam(value = "pasCheck[]") List<Long> pasCheck) {
        boolean result = pasOrderService.deletePasOrderList(pasCheck);
        return result;
    }

    @ResponseBody
    @GetMapping("/order/delete-order/soso") //pas 주문 내역 삭제
    public boolean deleteSosoOrderList(@RequestParam(value = "sosoCheck[]") List<Long> sosoCheck) {
        boolean result = sosoOrderService.deleteSosoOrderList(sosoCheck);
        return result;
    }


}
