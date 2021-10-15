package soso.sosoproject.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import soso.sosoproject.service.admin.member.AdminMemberService;

import java.util.List;

@RestController
@RequestMapping("admin/member")
public class MemberApiController {

    @Autowired
    AdminMemberService adminMemberService;


    @GetMapping("delete-MemberList")
    public void deleteMember(@RequestParam(value = "condition", defaultValue = "delete") String condition,
                             @RequestParam(value = "memberCheck[]", required = false) List<Long> memberSq) {

        if (condition.equals("delete")) {
            adminMemberService.deleteMember(memberSq);
        }
    }

}
