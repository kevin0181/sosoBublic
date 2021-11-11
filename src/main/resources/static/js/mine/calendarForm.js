var nowDate = new Date();


(function () {
    calendarMaker($("#calendarForm"), new Date());
    var startDate = new Date();

    var sYear = startDate.getFullYear();
    var sMonth = (startDate.getMonth() + 1);
    var sDate = startDate.getDate();
    var selectD = sYear + "-" + sMonth + "-" + sDate;

    $.ajax({
        data: {
            'date': selectD
        },
        url: "/user/Reserve/calendar/getList",
        type: "GET",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].orderDate == (selectD + " " + "11:00~13:00")) {
                    $('#timeFirst').attr("disabled", true);
                    $('#timeFirst').removeClass('activeGreenColor');
                    $('#timeFirst').text("11:00~13:00 예약중 입니다.");
                }
                if (data[i].orderDate == (selectD + " " + "13:00~15:00")) {
                    $('#timeMiddle').attr("disabled", true);
                    $('#timeMiddle').removeClass('activeGreenColor');
                    $('#timeMiddle').text("13:00~15:00 예약중 입니다.");
                }
                if (data[i].orderDate == (selectD + " " + "15:00~17:00")) {
                    $('#timeLast').attr("disabled", true);
                    $('#timeLast').removeClass('activeGreenColor');
                    $('#timeLast').text("15:00~17:00 예약중 입니다.");
                }
            }
        }
    });

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
            tag += "<td onclick='selectDate(this)' style='background-color: #cbdeff;' id='todayColor'>" + i + "</td>";
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
            "<th colspan='5' style='text-align: center;'><p><span id='yearId'>" + year + "</span>년 <span id='monthId'>" + month + "</span>월</p></th>" +
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

    $('#timeFirst').attr("disabled", false);
    $('#timeFirst').addClass('activeGreenColor');
    $('#timeFirst').text("11:00~13:00");
    $('#timeMiddle').attr("disabled", false);
    $('#timeMiddle').addClass('activeGreenColor');
    $('#timeMiddle').text("13:00~15:00");
    $('#timeLast').attr("disabled", false);
    $('#timeLast').addClass('activeGreenColor');
    $('#timeLast').text("15:00~17:00");

    var tagId = $(date).attr('id');

    if (tagId == "todayColor") {
        $('#todayColor').css("background-color", "#cbdeff");
    } else {
        $('#todayColor').css("background-color", "#ffffff");
    }


    var sYear = $('#yearId').text();
    var sMonth = $('#monthId').text();
    var sDate = $(date).text();
    var selectD = sYear + "-" + sMonth + "-" + sDate;

    $.ajax({
        data: {
            'date': selectD
        },
        url: "/user/Reserve/calendar/getList",
        type: "GET",
        success: function (data) {

            console.log(data);

            if (data.length != 0) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].orderDate == (selectD + " " + "11:00~13:00")) {
                        $('#timeFirst').attr("disabled", true);
                        $('#timeFirst').removeClass('activeGreenColor');
                        $('#timeFirst').text("11:00~13:00 예약중 입니다.");
                    }
                    if (data[i].orderDate == (selectD + " " + "13:00~15:00")) {
                        $('#timeMiddle').attr("disabled", true);
                        $('#timeMiddle').removeClass('activeGreenColor');
                        $('#timeMiddle').text("13:00~15:00 예약중 입니다.");
                    }
                    if (data[i].orderDate == (selectD + " " + "15:00~17:00")) {
                        $('#timeLast').attr("disabled", true);
                        $('#timeLast').removeClass('activeGreenColor');
                        $('#timeLast').text("15:00~17:00 예약중 입니다.");
                    }
                }
            }
        }
    });
}

function orderSosoToReserve(memberSq, Data) {
    if (memberSq == null) {
        alert("로그인을 해주세요.");
        location.href = "/user/account/login";
        return false;
    }
    console.log($(Data).val());
    console.log(memberSq);
}
