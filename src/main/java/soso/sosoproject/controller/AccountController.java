package soso.sosoproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import soso.sosoproject.dto.SignupDTO;
import soso.sosoproject.message.AccountMessage;
import soso.sosoproject.service.Account.MemberSignupService;

import javax.validation.Valid;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private MemberSignupService memberSignupService;

    //로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "/account/login";
    }

    //회원가입 페이지
    @GetMapping("/signupPage")
    public String signupPage(SignupDTO signupDTO) {
        return "/account/signupPage";
    }

    @GetMapping("/signup/member")
    public String signupPage2(SignupDTO signupDTO) {
        return "/account/signupPage";
    }

    //개인정보 활용 동의 페이지
    @GetMapping("/privacyPolicy")
    public String privacyPolicy() {
        return "/account/privacyPolicy";
    }

    //회원가입
    @PostMapping("/signup/member")
    public String signupMember(@Valid SignupDTO signupDTO, BindingResult bindingResult, Model model) {

        //유효성 검사
        if (bindingResult.hasErrors()) {
            return "account/signupPage";
        }

        //alert
        model.addAttribute("data", new AccountMessage("회원가입이 완료 되었습니다.", "/account/login"));

        memberSignupService.save(signupDTO);


        return "message/account-message";
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
