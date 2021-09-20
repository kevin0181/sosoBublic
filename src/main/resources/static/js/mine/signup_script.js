var email_check_result = false;
var email_certification_result = false;
var cer_random_number = "";
var check_email_disable_num = 0;


//email input 태그 누를때
function emailInput() {
    var emailCheckBtn = document.getElementById("emailCheckBtn");
    var sameEmailBtn = document.getElementById("sameEmailBtn");

    emailCheckBtn.disabled = false;
    sameEmailBtn.disabled = false;

    check_email_disable_num = 0;

}

//이메일 인증 버튼 누를때
function certificationEmailSendBtn() {
    var email = document.getElementById("email").value;

    if (!email) {
        alert("이메일을 입력해주세요.");
        return false;
    } else {
        alert("인증메일을 성공적으로 전송하였습니다.");

        $.ajax({
            url: "/user/account/certificationEmail/check",
            type: "post",
            dataType: "text",
            data: {"email": email},
            success: function (data) {
                cer_random_number = data;
            }
        });
    }
}

//인증이긴하지만 여긴 보여주는곳 나머지는 백단에서 실질적인 인증을하자!
function checkCertificationEmailBtn() {
    var certification_emailNumber = document.getElementById("certification-emailNumber").value;
    var emailCheckCloseBtn = document.getElementById("emailCheckCloseBtn");
    var emailCheckBtn = document.getElementById("emailCheckBtn");
    var email = document.getElementById("email");
    if (!certification_emailNumber) {
        alert("인증 번호를 입력 해주세요.");
        return false;
    } else {
        if (cer_random_number == certification_emailNumber) {
            alert("인증되었습니다.");
            emailCheckCloseBtn.click();
            emailCheckBtn.disabled = true;
            check_email_disable_num++;
            email_certification_result = true;
        } else {
            alert("인증에 실패하였습니다.");
        }
    }
}

//이메일 중복 체크
function same_check() {
    var email = document.getElementById("email").value;
    var sameEmailBtn = document.getElementById("sameEmailBtn");
    email_check_result = true;
    if (!email) {
        alert("이메일을 입력 해주세요.");
        email.focus();
        return false;
    } else {
        $.ajax({
            url: "/user/account/sameEmail/check",
            type: "post",
            dataType: "json",
            data: {"email": email},
            success: function (data) {

                if (data == false) {
                    alert("사용 가능한 이메일 입니다.");
                    sameEmailBtn.disabled = true;
                    check_email_disable_num++;
                } else if (data == true) {
                    alert("사용 불가능한 이메일 입니다.")
                } else {
                    alert("이메일을 입력 해주세요.");
                }

            }

        });
    }


}


//회원가입 버튼 클릭 시
function signUp() {
    var password = document.getElementById("password").value;
    var rtpassword = document.getElementById("RtPassword").value;
    var policy = document.getElementById("policy").checked;
    var same_email_check_btn = document.getElementById("sameEmailBtn");
    var emailCheckBtn = document.getElementById("emailCheckBtn");

    var form = document.getElementById("signup-form");

    if (email_check_result == false) {
        alert("이메일 중복체크가 되어 있지 않습니다.");
        same_email_check_btn.focus();
        return false;
    }

    if (policy != true) {
        alert("개인정보 활용 동의가 체크 되어 있지 않습니다.");
        policy.focus();
        return false;
    }

    if (email_certification_result == false) {
        alert("이메일 인증이 되어있지 않습니다.");
        emailCheckBtn.focus();
        return false;
    }

    if (check_email_disable_num == 0) {
        alert("인증이 완료되지않은 부분이 있습니다.");
        return false;
    }

    if (password != rtpassword) {
        alert("비밀번호가 일치하지 않습니다.");
        rtpassword.focus();
        return false;
    } else {
        form.submit();
    }


}