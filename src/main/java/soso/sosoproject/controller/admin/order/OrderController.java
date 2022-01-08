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
import java.util.*;

@Controller
@RequestMapping("admin")
public class OrderController {

    @Autowired
    private PasOrderService pasOrderService;

    @Autowired
    private SosoOrderService sosoOrderService;

    @GetMapping("/orderList") //현재 주문 내역 가져오기
    public String startOrderList(@RequestParam("className") String className, Model model) {

        if (className.equals("pas")) {
            //pas list get
            List<PasOrderDTO> pasOrderDTOList = pasOrderService.findAllPlaceAndEnableOrder();

            for (int i = 0; i < pasOrderDTOList.size(); i++) {
                LocalDateTime parsedLocalDateTime = LocalDateTime.parse(pasOrderDTOList.get(i).getOrderDate());
                String viewDateyyyy = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String viewDatedddd = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                String viewDate = viewDateyyyy + " " + viewDatedddd;
                pasOrderDTOList.get(i).setOrderDate(viewDate);
            }

            model.addAttribute("orderList", pasOrderDTOList);
            model.addAttribute("className", className);
            return "admin/Order/PasOrderList";
        } else if (className.equals("soso")) {
            //soso list get
            List<SosoOrderDTO> sosoOrderServices = sosoOrderService.findAllPlaceAndEnableOrder();
            Collections.reverse(sosoOrderServices);

            for (int i = 0; i < sosoOrderServices.size(); i++) {
                LocalDateTime parsedLocalDateTime = LocalDateTime.parse(sosoOrderServices.get(i).getOrderDate());
                String viewDateyyyy = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String viewDatedddd = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                String viewDate = viewDateyyyy + " " + viewDatedddd;
                sosoOrderServices.get(i).setOrderDate(viewDate);
            }

            model.addAttribute("orderList", sosoOrderServices);
            model.addAttribute("className", className);
            return "admin/Order/SosoOrderList";
        }

        model.addAttribute("data", new AccountMessage("잘못된 접근입니다.(관리자에게 문의해주세요.)", "/admin/index"));
        return "/message/account-message";
    }

    @GetMapping("/All/OrderList") //주문 전체 내역 가져오기
    public String sosoOrPasOrderAllList(@RequestParam(value = "className") String className, @RequestParam(value = "dateSize", required = false) String date, Model model) {
        String orderDate;
        String viewDate;
        List deleteListNumber = new ArrayList();
        if (className.equals("sosoList")) {
            List<SosoOrderDTO> sosoOrderDTOList = sosoOrderService.findAllOrderList();
            deleteListNumber.clear();
            if (date == null || date.equals("")) { //날짜지정없으면 현재 당일 달로 가져오기.
                for (int i = 0; i < sosoOrderDTOList.size(); i++) {
                    orderDate = sosoOrderDTOList.get(i).getOrderDate();
                    viewDate = sosoOrderDTOList.get(i).getOrderDate();
                    LocalDateTime parsedLocalDateTime = LocalDateTime.parse(orderDate);
                    LocalDateTime parsedLocalDateTimeView = LocalDateTime.parse(viewDate);

                    // LocalDateTime에서 필요한 내용 필요한 형식으로 뽑기
                    String yyyyMM = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                    String viewDateyyyy = parsedLocalDateTimeView.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String viewDatedddd = parsedLocalDateTimeView.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    viewDate = viewDateyyyy + " " + viewDatedddd;


                    Date nowDate = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                    String strNowMonth = simpleDateFormat.format(nowDate);

                    sosoOrderDTOList.get(i).setOrderDate(viewDate);

                    if (!yyyyMM.equals(strNowMonth)) {
                        deleteListNumber.add(i);
                    }
                }
                if (deleteListNumber.size() != 0) {
                    int result = 0;
                    Collections.reverse(deleteListNumber); // 뒤집기
                    for (int j = 0; j < deleteListNumber.size(); j++) {
                        result = (int) (deleteListNumber.get(j));
                        sosoOrderDTOList.remove(result);
                    }
                }
            } else if (date.equals("all")) {
                model.addAttribute("orderList", sosoOrderDTOList);
                //active
                model.addAttribute("className", className);
                return "admin/Order/all/sosoAllList";
            } else {
                for (int i = 0; i < sosoOrderDTOList.size(); i++) {
                    orderDate = sosoOrderDTOList.get(i).getOrderDate();
                    viewDate = sosoOrderDTOList.get(i).getOrderDate();
                    LocalDateTime parsedLocalDateTime = LocalDateTime.parse(orderDate);
                    LocalDateTime parsedLocalDateTimeView = LocalDateTime.parse(viewDate);

                    // LocalDateTime에서 필요한 내용 필요한 형식으로 뽑기
                    String yyyyMM = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String viewDateyyyy = parsedLocalDateTimeView.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String viewDatedddd = parsedLocalDateTimeView.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    viewDate = viewDateyyyy + " " + viewDatedddd;

                    sosoOrderDTOList.get(i).setOrderDate(viewDate);

                    if (!yyyyMM.equals(date)) {
                        deleteListNumber.add(i);
                    }
                }
                if (deleteListNumber.size() != 0) {
                    int result = 0;
                    Collections.reverse(deleteListNumber); // 뒤집기
                    for (int i = 0; i < deleteListNumber.size(); i++) {
                        result = (int) (deleteListNumber.get(i));
                        sosoOrderDTOList.remove(result);
                    }
                }
            }


            model.addAttribute("orderList", sosoOrderDTOList);

            //active
            model.addAttribute("className", className);
            return "admin/Order/all/sosoAllList";

        } else if (className.equals("pasList")) {
            List<PasOrderDTO> pasOrderDTOList = pasOrderService.findAllOrderList();

            deleteListNumber.clear();
            if (date == null || date.equals("")) { //날짜지정없으면 현재 당일 달로 가져오기.
                for (int i = 0; i < pasOrderDTOList.size(); i++) {

                    orderDate = pasOrderDTOList.get(i).getOrderDate();
                    viewDate = pasOrderDTOList.get(i).getOrderDate();
                    LocalDateTime parsedLocalDateTime = LocalDateTime.parse(orderDate);
                    LocalDateTime parsedLocalDateTimeView = LocalDateTime.parse(viewDate);

                    // LocalDateTime에서 필요한 내용 필요한 형식으로 뽑기
                    String yyyyMM = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
                    String viewDateyyyy = parsedLocalDateTimeView.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String viewDatedddd = parsedLocalDateTimeView.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    viewDate = viewDateyyyy + " " + viewDatedddd;


                    Date nowDate = new Date();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
                    String strNowMonth = simpleDateFormat.format(nowDate);

                    pasOrderDTOList.get(i).setOrderDate(viewDate);
                    if (!yyyyMM.equals(strNowMonth)) {
                        deleteListNumber.add(i);
                    }
                }
                if (deleteListNumber.size() != 0) {
                    int result = 0;
                    Collections.reverse(deleteListNumber); // 뒤집기
                    for (int j = 0; j < deleteListNumber.size(); j++) {
                        result = (int) (deleteListNumber.get(j));
                        pasOrderDTOList.remove(result);
                    }
                }
            } else if (date.equals("all")) {
                model.addAttribute("orderList", pasOrderDTOList);
                //active
                model.addAttribute("className", className);
                return "admin/Order/all/pasAllList";
            } else {
                for (int i = 0; i < pasOrderDTOList.size(); i++) {
                    orderDate = pasOrderDTOList.get(i).getOrderDate();
                    viewDate = pasOrderDTOList.get(i).getOrderDate();
                    LocalDateTime parsedLocalDateTime = LocalDateTime.parse(orderDate);
                    LocalDateTime parsedLocalDateTimeView = LocalDateTime.parse(viewDate);

                    // LocalDateTime에서 필요한 내용 필요한 형식으로 뽑기
                    String yyyyMM = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String viewDateyyyy = parsedLocalDateTimeView.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String viewDatedddd = parsedLocalDateTimeView.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

                    viewDate = viewDateyyyy + " " + viewDatedddd;

                    pasOrderDTOList.get(i).setOrderDate(viewDate);

                    if (!yyyyMM.equals(date)) {
                        deleteListNumber.add(i);
                    }
                }
                if (deleteListNumber.size() != 0) {
                    int result = 0;
                    Collections.reverse(deleteListNumber); // 뒤집기
                    for (int i = 0; i < deleteListNumber.size(); i++) {
                        result = (int) (deleteListNumber.get(i));
                        pasOrderDTOList.remove(result);
                    }
                }
            }


            model.addAttribute("orderList", pasOrderDTOList);
            //active
            model.addAttribute("className", className);
            return "admin/Order/all/pasAllList";

        } else {
            return "error/error-404-new";
        }
    }

    @GetMapping("/order/changeDetail")
    public String changeDetailOrderGoPage(@RequestParam(value = "memberSq") String memberSq,
                                          @RequestParam(value = "uid") String uid,
                                          @RequestParam(value = "place") String place, Model model) {

        if (place.equals("pas")) {
            PasOrderDTO pasOrderDTO = pasOrderService.findOrderId(uid);

            LocalDateTime parsedLocalDateTime = LocalDateTime.parse(pasOrderDTO.getOrderDate());
            String viewDateyyyy = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String viewDatedddd = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            String viewDate = viewDateyyyy + " " + viewDatedddd;
            pasOrderDTO.setOrderDate(viewDate);

            model.addAttribute("pasOrderDTO", pasOrderDTO);
            model.addAttribute("place", "pas");
            return "/admin/Order/OrderDetailChange";
        } else if (place.equals("soso")) {
            SosoOrderDTO sosoOrderDTO = sosoOrderService.findOrderUid(uid);

            LocalDateTime parsedLocalDateTime = LocalDateTime.parse(sosoOrderDTO.getOrderDate());
            String viewDateyyyy = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String viewDatedddd = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            String viewDate = viewDateyyyy + " " + viewDatedddd;
            sosoOrderDTO.setOrderDate(viewDate);


            model.addAttribute("sosoOrderDTO", sosoOrderDTO);
            model.addAttribute("place", "soso");
            return "/admin/Order/OrderDetailChange";
        } else {
            return "/error/error-404-new";
        }
    }

    @PostMapping("/detail/order/change/soso")
    public String sosoChangeDetailOrder(SosoOrderDTO sosoOrderDTO, Model model) throws ParseException {

//        String dateString = sosoOrderDTO.getOrderDate();
//        LocalDateTime parsedLocalDateTime = LocalDateTime.parse(dateString);
//
//        // LocalDateTime에서 필요한 내용 필요한 형식으로 뽑기
//        String yyyyMMdd = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        String HHmmss = parsedLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//        String date = yyyyMMdd + " " + HHmmss;
//
//        Optional<SosoOrderDTO> sosoOrderDTOGetId = sosoOrderService.findAllById(sosoOrderDTO);
//
//        sosoOrderDTOGetId.get().setOrderDate(date);
//
//        sosoOrderService.saveSosoOrder(sosoOrderDTOGetId.get());

        sosoOrderService.saveSosoOrder(sosoOrderDTO);
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
