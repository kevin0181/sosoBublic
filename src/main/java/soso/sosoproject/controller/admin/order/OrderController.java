package soso.sosoproject.controller.admin.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.message.AccountMessage;
import soso.sosoproject.service.order.PasOrderService;
import soso.sosoproject.service.order.SosoOrderService;

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

            model.addAttribute("orderList", sosoOrderServices);
            model.addAttribute("className", className);
            return "admin/Order/SosoOrderList";
        }

        model.addAttribute("data", new AccountMessage("잘못된 접근입니다.(관리자에게 문의해주세요.)", "/admin/index"));
        return "/message/account-message";
    }
}
