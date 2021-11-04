package soso.sosoproject.controller.admin.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin")
public class OrderController {
    @GetMapping("/orderList")
    public String startOrderList(@RequestParam("className") String className, Model model) {

        if (className.equals("soso")) {
            model.addAttribute("className", className);
            return "admin/Order/orderList";
        } else if (className.equals("pas")) {
            model.addAttribute("className", className);
            return "admin/Order/orderList";
        }
        return "admin/Order/orderList";
    }
}
