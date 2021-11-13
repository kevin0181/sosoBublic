package soso.sosoproject.controller.admin.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soso.sosoproject.dto.SosoMenuDTO;
import soso.sosoproject.message.AccountMessage;
import soso.sosoproject.service.admin.menu.SosoMenuService;

@Controller
@RequestMapping("menu")
public class SosoMenuController {


    @Autowired
    private SosoMenuService sosoMenuService;

    //소소한부엌 -------------------------------------------------------------------------------------------------------------------

    //메뉴 추가
    @PostMapping("/soso/addmenu")
    public String addSosoMenu(SosoMenuDTO sosoMenuDTO, Model model) {

        sosoMenuService.saveMenu(sosoMenuDTO);

        model.addAttribute("data", new AccountMessage("메뉴를 추가하였습니다.", "/admin/soso/addMenu?className=sosoMenu"));
        return "/message/account-message";
    }

    @GetMapping("/soso/deleteMenu")
    public String deleteSosoMenu(@RequestParam(value = "menuSq") Long sosoMenuSq, Model model) {

        sosoMenuService.deleteMenu(sosoMenuSq);

        model.addAttribute("data", new AccountMessage("메뉴를 삭제하였습니다.", "/admin/soso/addMenu?className=sosoMenu"));
        return "/message/account-message";
    }
}
