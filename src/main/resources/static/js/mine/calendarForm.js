var nowDate = new Date();


(function () {
    calendarMaker($("#calendarForm"), new Date());
    var startDate = new Date();

    var sYear = startDate.getFullYear();
    var sMonth = (startDate.getMonth() + 1);
    var sDate = startDate.getDate();
    var selectD = sYear + "-" + sMonth + "-" + sDate;

    $('#sosoOrderDateHidden').val(selectD);
    $('#sosoOrderDate').val(selectD);

})();


function calendarMaker(target, date) {
    var todayMonth = new Date();
    if (date == null || date == undefined) {
        date = new Date();
    }
    nowDate = date;
    if ($(target).length > 0) {
        var year = nowDate.getFullYear();
        var month = nowDate.getMonth() + 1;
        var getD = nowDate.getDate();
        $(target).empty().append(assembly(year, month));
        if ((todayMonth.getMonth() + 1) == month) {
            $('.prev').attr('disabled', true);
        }
    } else {
        console.error("custom_calendar Target is empty!!!");
        return;
    }

    var thisMonth = new Date(nowDate.getFullYear(), nowDate.getMonth(), 1);
    var thisLastDay = new Date(nowDate.getFullYear(), nowDate.getMonth() + 1, 0);


    var tag = "<tr style='height: 40px'>";
    var cnt = 0;
    //빈 공백 만들어주기
    for (i = 0; i < thisMonth.getDay(); i++) {
        tag += "<td></td>";
        cnt++;
    }

    //날짜 채우기
    for (i = 1; i <= thisLastDay.getDate(); i++) {
        if (cnt % 7 == 0) {
            tag += "<tr style='height: 40px'>";
        }

        if (i == getD && year == todayMonth.getFullYear() && month == (todayMonth.getMonth() + 1)) {
            tag += "<td onclick='selectDate(this)' style='border: 2px solid #d1ddd7;' id='todayColor'>" + i + "</td>";
        } else {
            tag += "<td onclick='selectDate(this)'>" + i + "</td>";
        }

        cnt++;
        if (cnt % 7 == 0) {
            tag += "</tr style='height: 40px'>";
        }
    }
    $(target).find("#custom_set_date").append(tag);
    calMoveEvtFn();

    function assembly(year, month) {
        var calendar_html_code =
            "<table class='custom_calendar_table'>" +
            "<colgroup>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "<col style='width:81px'/>" +
            "</colgroup>" +
            "<thead class='cal_date'>" +
            "<th><button type='button' class='prev'><</button></th>" +
            "<th colspan='5' style='text-align: center;'><p style='color:#abcbbb;'><span id='yearId'>" + year + "</span>년 <span id='monthId'>" + month + "</span>월</p></th>" +
            "<th><button type='button' class='next'>></button></th>" +
            "</thead>" +
            "<thead  class='cal_week' style='text-align: center; height: 30px;'>" +
            "<th>일</th><th>월</th><th>화</th><th>수</th><th>목</th><th>금</th><th>토</th>" +
            "</thead>" +
            "<tbody id='custom_set_date'>" +
            "</tbody>" +
            "</table>";
        return calendar_html_code;
    }

    function calMoveEvtFn() {
        //전달 클릭
        $(".custom_calendar_table").on("click", ".prev", function () {
            nowDate = new Date(nowDate.getFullYear(), nowDate.getMonth() - 1, nowDate.getDate());
            calendarMaker($(target), nowDate);
        });
        //다음날 클릭
        $(".custom_calendar_table").on("click", ".next", function () {
            nowDate = new Date(nowDate.getFullYear(), nowDate.getMonth() + 1, nowDate.getDate());
            calendarMaker($(target), nowDate);
        });
        //일자 선택 클릭
        $(".custom_calendar_table").on("click", "td", function () {
            $(".custom_calendar_table .select_day").removeClass("select_day");
            $(this).removeClass("select_day").addClass("select_day");
        });
    }


}

function selectDate(date) {

    var tagId = $(date).attr('id');

    if (tagId == "todayColor") {
        $('#todayColor').css("border", "2px solid #d1ddd7");
    } else {
        $('#todayColor').css("border", "none");
    }


    var sYear = $('#yearId').text();
    var sMonth = $('#monthId').text();
    var sDate = $(date).text();
    var selectD = sYear + "-" + sMonth + "-" + sDate;

    $('#sosoOrderDateHidden').val(selectD);
    $('#sosoOrderDate').val(selectD);

}


var regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; //이메일 정규식
var phoneExp = /^(\d{10,11}|\d{3}-\d{3,4}-\d{4})$/i;

function sosoOrder(memberSq, memberEMail) {

    if (memberSq == null) {
        alert("로그인을 해주세요.");
        location.href = "/user/account/login";
        return false;
    } else if ($('#sosoOrderName').val() == "") {
        alert("예약자 성함을 채워주세요.");
        $('#sosoOrderName').focus();
        return false;
    } else if ($('#sosoOrderPhoneNumber').val() == "") {
        alert("전화번호를 입력해주세요.");
        $('#sosoOrderPhoneNumber').focus();
        return false;
    } else if ($('#sosoOrderAddress').val() == "") {
        alert("주소를 채워주세요.");
        $('#sosoOrderAddress').focus();
        return false;
    } else if ($('#sosoOrderDate').val() == "") {
        alert("시간을 선택해주세요. (에러)");
        location.href = "/user/Reserve/calendar";
        return false;
    } else if ($('#sosoOrderTime').val() == "") {
        alert("시간을 입력해주세요.");
        $('#sosoOrderTime').focus();
        return false;
    } else if ($('#sosoOrderCheckbox').is(":checked") != true) {
        alert("주문 정보 활용 동의를 체크해주세요.");
        return false;
    } else {
        if (phoneExp.test($('#sosoOrderPhoneNumber').val()) != true) {
            alert("전화번호 형식에 맞지 않습니다. (-)를 빼주세요.");
            return false;
        } else if ($('#selectPayVale').val() == "none") {
            alert("지불 방식을 선택해주세요.");
            return false;
        } else {

            var totalPrice = 1;//결제금액 백에서 결제금액 체크 필수!

            var form = $("#sosoOrderForm")[0];
            var formData = new FormData(form);
            formData.append("orderDate", $('#sosoOrderDateHidden').val() + " " + $('#sosoOrderTime').val());
            formData.append("orderPlace", "soso");
            formData.append("ordersTotalPrice", totalPrice);

            var uid;
            var DateUid = new Date();
            uid = DateUid.getFullYear() + "" + (DateUid.getMonth() + 1) + "" + DateUid.getDate() + "" + DateUid.getHours() + "" + DateUid.getMinutes() + "" + DateUid.getSeconds() + "" + rand(1, 9999) + "S";

            IMP.init('imp76725859');
            IMP.request_pay({ // param
                pg: $('#selectPayVale').val(),
                pay_method: "card",
                merchant_uid: uid,
                name: "소소한 부엌",
                amount: totalPrice,
                buyer_email: memberEMail,
                buyer_name: $('#sosoOrderName').val(),
                buyer_tel: $('#sosoOrderPhoneNumber').val(),
                buyer_addr: $('#sosoOrderAddress').val(),
            }, function (rsp) { // callback
                if (rsp.success) {
                    if (rsp.merchant_uid == uid) {
                        formData.append("ordersMerchantUid", uid);
                        formData.append("ordersImpUid", rsp.imp_uid);
                    } else {
                        alert("결제 정보 오류가 발생하였습니다."); //주문번호가 일치하지않음 //백에서 검사한번더해야함.
                        return false;
                    }
                    $.ajax({
                        url: "/user/Reserve/calendar/order",
                        type: "post",
                        dataType: "json",
                        contentType: false,
                        processData: false,
                        data: formData,
                        success: function (data) {
                            if (data.error == "error7001") {
                                alert("주문하는 장소 오류가 발생하였습니다.  : 7001");
                                location.href = "/user/Reserve/calendar";
                                return false;
                            } else if (data.error == "error7002") {
                                alert("주문번호가 일치하지않습니다.(관리자에게 문의해주세요.)  : 7002");
                                location.href = "/user/Reserve/calendar";
                                return false;
                            } else if (data.error == "error7003") {
                                alert("주문 가격이 일치하지않습니다.(관리자에게 문의해주세요.)  : 7003");
                                location.href = "/user/Reserve/calendar";
                                return false;
                            } else {
                                alert("주문이 완료되었습니다.");
                                location.href = "/user/index";
                            }
                        }
                    });
                } else {

                }
            });


        }

    }

}


function rand(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}






