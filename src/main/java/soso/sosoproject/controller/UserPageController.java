package soso.sosoproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserPageController {

    @GetMapping("/")
    public String start() {
        return "/user/index";
    }

    @GetMapping("/user/index")
    public String index() {
        return "/user/index";
    }

    @GetMapping("/user/menu")
    public String menu() {
        return "/user/menu";
    }

    @GetMapping("/user/blog")
    public String blog() {
        return "/user/blog-home";
    }

    @GetMapping("/user/about")
    public String about() {
        return "/user/about";
    }

    @GetMapping("/user/blog-single")
    public String blogSingle() {
        return "/user/blog-single";
    }

    //그냥 만들어둠 나중에 쓸꺼같아서
    @GetMapping("/user/elements")
    public String elements() {
        return "/user/elements";
    }

}
