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
public class AccountController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private EmailSendService emailSendService;

    String certNumber;

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
        memberService.save(memberDTO);

        //alert
        model.addAttribute("data", new AccountMessage("회원가입이 완료 되었습니다.", "/user/account/login"));


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
        certNumber = emailSendService.sendService(email);
        return certNumber;
    }

}
