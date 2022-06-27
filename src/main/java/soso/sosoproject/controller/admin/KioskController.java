package soso.sosoproject.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KioskController {

    @GetMapping("/admin/kiosk/addMenu")
    public String addKioskMenu(@RequestParam("className") String className, Model model) {

        model.addAttribute("className", className);
        return "admin/kiosk/kioskAddMenu";
    }

}
