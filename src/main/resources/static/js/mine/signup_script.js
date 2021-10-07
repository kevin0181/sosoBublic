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
    var emailInput = document.getElementById("email");
    var email = emailInput.value;
    var sameEmailBtn = document.getElementById("sameEmailBtn");
    var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    email_check_result = true;
    if (!email) {
        alert("이메일을 입력 해주세요.");
        emailInput.focus();
        return false;
    } else if (email.match(regExp) == null) {
        alert("이메일 형식에 맞춰서 작성해주세요.");
        setTimeout(function () {
            emailInput.focus();
        }, 1);
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
    var password = document.getElementById("f-password");
    var rtpassword = document.getElementById("s-password");
    var name = document.getElementById("name");
    var address = document.getElementById("address");
    var phone = document.getElementById("phoneNumber");
    var policy = document.getElementById("policy");
    var same_email_check_btn = document.getElementById("sameEmailBtn");
    var emailCheckBtn = document.getElementById("emailCheckBtn");

    var form = document.getElementById("signup-form");

    if (email_check_result == false) {
        alert("이메일 중복체크가 되어 있지 않습니다.");
        same_email_check_btn.focus();
        return false;
    } else if (email_certification_result == false) {
        alert("이메일 인증이 되어있지 않습니다.");
        emailCheckBtn.focus();
        return false;
    } else if (!password.value) {
        alert("비밀번호가 입력되지 않았습니다.");
        password.focus();
        return false;
    } else if (!rtpassword.value) {
        alert("비밀번호 확인이 입력되지 않았습니다.");
        rtpassword.focus();
        return false;
    } else if (password.value != rtpassword.value) {
        alert("비밀번호가 일치하지 않습니다.");
        rtpassword.focus();
        return false;
    } else if (!name.value) {
        alert("이름이 입력되지 않았습니다.");
        name.focus();
        return false;
    } else if (!address.value) {
        alert("주소가 입력되지 않았습니다.");
        address.focus();
        return false;
    } else if (!phone.value) {
        alert("핸드폰 번호가 입력되지 않습니다.");
        phone.focus();
        return false;
    } else if (policy.checked != true) {
        alert("개인정보 활용 동의가 체크 되어 있지 않습니다.");
        return false;
    } else {
        if (form.checkValidity() == false) {
            form.classList.add("was-validated")
        } else {
            form.submit();
        }
    }
}

// oauth2 회원가입
function signUpOauth2() {
    if (!$('#name-oauth2').val()) {
        alert("이름을 입력해주세요.");
        $('#name-oauth2').focus();
        return false;
    } else if (!$('#address-oauth2')) {
        alert("주소를 입력해주세요.");
        $('#address-oauth2').focus();
        return false;
    } else if (!$('#phoneNumber-oauth2')) {
        alert("전화번호를 입력해주세요.");
        $('#phoneNumber-oauth2').focus();
        return false;
    } else if ($('#policy-oauth2').checked != true) {
        alert("개인정보 활용 동의가 체크 되어 있지 않습니다.");
        return false;
    } else {
        $('#oauth2-form').submit();
    }
}