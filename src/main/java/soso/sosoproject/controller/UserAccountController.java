package soso.sosoproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.message.AccountMessage;
import soso.sosoproject.service.Account.EmailSendService;
import soso.sosoproject.service.Account.MemberService;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
@RequestMapping("/user/account")
public class UserAccountController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private EmailSendService emailSendService;

    String certificationKey;

    //로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "/user/account/login";
    }

    //회원가입 페이지
    @GetMapping("/signupPage")
    public String signupPage(MemberDTO memberDTO) {
        return "/user/account/signupPage";
    }

    //개인정보 활용 동의 페이지
    @GetMapping("/privacyPolicy")
    public String privacyPolicy() {
        return "/user/account/privacyPolicy";
    }

    //회원가입
    @PostMapping("/signup")
    public String signupMember(@Valid MemberDTO memberDTO, BindingResult bindingResult, Model model) {

        //유효성 검사
        if (bindingResult.hasErrors()) {
            return "/user/account/signupPage";
        }


        //회원가입
        boolean result = memberService.save(memberDTO, certificationKey);

        if (result) {
            //alert(success)
            model.addAttribute("data", new AccountMessage("회원가입이 완료 되었습니다.", "/user/account/login"));
        } else {
            //alert(fail)
            model.addAttribute("data", new AccountMessage("회원가입에 실패하였습니다.", "/user/account/signupPage"));
        }


        return "/message/account-message";
    }

    //이메일 중복 체크
    @PostMapping("/sameEmail/check")
    @ResponseBody
    public boolean memberEmailSameCheck(@RequestParam(value = "email") String email) {
        boolean result = memberService.emailCheck(email);
        return result;
    }

    //이메일 인증
    @PostMapping("/certificationEmail/check")
    @ResponseBody
    public String certificationCheck(@RequestParam(value = "email") String email) throws MessagingException {
        certificationKey = emailSendService.certified_key();
        emailSendService.sendService(email, certificationKey);
        return certificationKey;
    }

    //아이디, 비번 찾는 페이지로 이동
    @GetMapping("/find")
    public String findAccount(@RequestParam(value = "kind") String kind) {

        if (kind.equals("id")) {
            return "/user/account/find/findId";
        } else if (kind.equals("password")) {
            return "/user/account/find/findPassword";
        }

        return "/user/account/login";
    }

    //아이디 찾기
    @PostMapping("findId")
    public String findId(MemberDTO memberDTO, Model model) {
        MemberDTO resultMemberDTO = memberService.findMemberId(memberDTO);
        int result;
        String Email = resultMemberDTO.getMemberEmail();
        int EmailIndex = Email.indexOf("@"); //@까지의 길이값
        String id = Email.substring(0, EmailIndex);
        char markingStart[];
        if (id.length() <= 4) {
            result = id.substring(1, 4).length();
            markingStart = new char[result];
            for (int i = 0; i > markingStart.length; i++) {
                markingStart[i] = '*';
            }
            id = id.substring(1, 4);
        } else {
            result = id.substring(4, EmailIndex).length();
            markingStart = new char[result];
            for (int i = 0; i < markingStart.length; i++) {
                markingStart[i] = '*';
            }
            id = id.substring(0, 4);
        }
        Email = new String(markingStart);
        String hidingEmail = id + Email + resultMemberDTO.getMemberEmail().substring(EmailIndex, resultMemberDTO.getMemberEmail().length());

        model.addAttribute("findMemberId", hidingEmail);

        return "/user/account/find/findId";
    }


    //패스워드 찾기
    @PostMapping("findPassword")
    public String findPassword() {
        return null;
    }

}
