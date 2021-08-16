package soso.sosoproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }

    @GetMapping("/blog")
    public String blog() {
        return "blog-home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }


    //그냥 만들어둠 나중에 쓸꺼같아서
    @GetMapping("/elements")
    public String elements() {
        return "elements";
    }

}
