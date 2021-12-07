package soso.sosoproject.controller.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import soso.sosoproject.service.user.InstagramService;

import java.io.IOException;

@ResponseBody
@Controller
public class InstagramApi {

    @Autowired
    private InstagramService instagramService;


    @GetMapping("/user/get/tag")
    public String getTag(@RequestParam(value = "tagName") String tagName) throws IOException {

        String respones = instagramService.searchTag(tagName);
        System.out.println(respones);
        return respones;
    }
}
