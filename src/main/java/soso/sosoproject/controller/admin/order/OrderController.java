package soso.sosoproject.controller.admin.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.service.order.OrderService;

import java.util.List;

@Controller
@RequestMapping("admin")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orderList")
    public String startOrderList(@RequestParam("className") String className, Model model) {

        if (className.equals("pas")) {

            //pas list get
            List<PasOrderDTO> pasOrderDTOList = orderService.findAllPlaceAndEnableOrder();

            model.addAttribute("orderList", pasOrderDTOList);
            model.addAttribute("className", className);
            return "admin/Order/orderList";
        } else if (className.equals("soso")) {

            //soso list get
            List<PasOrderDTO> pasOrderDTOList = orderService.findAllPlaceAndEnableOrder();

            model.addAttribute("orderList", pasOrderDTOList);
            model.addAttribute("className", className);
            return "admin/Order/orderList";
        }
        return "admin/Order/orderList";
    }
}
