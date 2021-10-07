package soso.sosoproject.controller.user;

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
        emailSendService.sendEmailCheck(email, certificationKey);
        return certificationKey;
    }

    //아이디, 비번 찾는 페이지로 이동
    @GetMapping("/find")
    public String findAccount(@RequestParam(value = "kind", required = false) String kind) {
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
        if (resultMemberDTO == null) {
            model.addAttribute("data", new AccountMessage("이름이나 전화번호가 맞지 않습니다.", "/user/account/find?kind=id"));
            return "/message/account-message";
        }
        String email = resultMemberDTO.getMemberEmail();
        int emailIndex = email.indexOf("@"); //@까지의 길이값
        String id = email.substring(0, emailIndex);
        char markingStart[];

        if (id.length() <= 4) {
            result = id.substring(1, 4).length();
            markingStart = new char[result];
            for (int i = 0; i > markingStart.length; i++) {
                markingStart[i] = '*';
            }
            id = id.substring(1, 4);
        } else {
            result = id.substring(4, emailIndex).length();
            markingStart = new char[result];
            for (int i = 0; i < markingStart.length; i++) {
                markingStart[i] = '*';
            }
            id = id.substring(0, 4);
        }

        email = new String(markingStart);
        String hidingEmail = id + email + resultMemberDTO.getMemberEmail().substring(emailIndex, resultMemberDTO.getMemberEmail().length());

        model.addAttribute("findMemberId", hidingEmail);

        return "/user/account/find/findId";
    }


    //패스워드 찾기
    @PostMapping("findPassword")
    public String findPassword(@RequestParam(value = "memberEmail") String email,
                               @RequestParam(value = "memberName") String name, Model model) throws MessagingException {
        MemberDTO resultMemberDTO = memberService.findMemberPassword(email, name);
        if (resultMemberDTO == null) {
            model.addAttribute("data", new AccountMessage("이메일 또는 이름이 맞지 않습니다.", "/user/account/find?kind=password"));
            return "/message/account-message";
        } else {
            certificationKey = emailSendService.certified_key();
            resultMemberDTO.setCertiNumber(certificationKey);
            memberService.reSave(resultMemberDTO);
            emailSendService.sendPasswordCheck(resultMemberDTO.getMemberEmail(), certificationKey);
            model.addAttribute("data", new AccountMessage("이메일을 전송했습니다.", "/user/index"));
            return "/message/account-message";
        }
    }

    //비밀번호 변경 이메일 -> 우리 페이지로
    @GetMapping("/change/password")
    public String changePasswordPage(@RequestParam(value = "email") String email,
                                     @RequestParam(value = "certi") String certi, Model model) {
        if (email == null || certi == null) {
            model.addAttribute("data", new AccountMessage("잘못된 접근입니다.", "/user/index"));
            return "/message/account-message";
        }

        MemberDTO resultMemberDTO = memberService.findMember(email);
        if (resultMemberDTO == null) {
            model.addAttribute("data", new AccountMessage("잘못된 접근입니다.(다시 시도해주시길 바랍니다.)", "/user/index"));
            return "/message/account-message";
        } else {
            if (resultMemberDTO.getCertiNumber().equals(certi)) {
                model.addAttribute("memberDTO", resultMemberDTO);
                return "/user/account/find/changePassword";
            } else {
                model.addAttribute("data", new AccountMessage("잘못된 접근입니다.(다시 시도해주시길 바랍니다.)", "/user/index"));
                return "/message/account-message";
            }
        }
    }

    //비밀번호 변경 부분
    @PostMapping("/change/password")
    public String changePassword(MemberDTO memberDTO, Model model) {
        certificationKey = emailSendService.certified_key();
        memberDTO.setCertiNumber(certificationKey);
        boolean result = memberService.reSave(memberDTO);
        if (result) {
            model.addAttribute("data", new AccountMessage("비밀번호가 변경되었습니다.", "/user/index"));
            return "/message/account-message";
        } else {
            model.addAttribute("data", new AccountMessage("비밀번호 변경 오류입니다.", "/user/index"));
            return "/message/account-message";
        }
    }

}
