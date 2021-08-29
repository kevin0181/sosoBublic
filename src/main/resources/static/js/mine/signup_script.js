var email_check_result = false;

function emailCheck() {
    var email_check_form = document.getElementById("email_check");
    var email = document.getElementById("email");

    email.style.borderRadius = ".25rem .25rem 0 0";
    email_check_form.style.display = "block";
}

function same_check() {
    var email = document.getElementById("email").value;
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
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var rtpassword = document.getElementById("RtPassword").value;
    var name = document.getElementById("name").value;
    var address = document.getElementById("address").value;
    var phoneNumber = document.getElementById("phoneNumber").value;
    var policy = document.getElementById("policy").checked;
    var email_check_btn = document.getElementById("sameEmailBtn");

    var form = document.getElementById("signup-form");

    if (email_check_result == false) {
        alert("이메일 중복체크가 되어 있지 않습니다.");
        email_check_btn.focus();
        return false;
    }
    if (policy != true) {
        alert("개인정보 활용 동의가 체크 되어 있지 않습니다.");
        policy.focus();
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
