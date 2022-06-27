var stompClient = null;

//시간 다시 보내줌
function OrderTime(ordersImpUid) {
    $.ajax({
        url: "/admin/order/pas/orderTime",
        type: "GET",
        data: {
            "ordersImpUid": ordersImpUid,
            "time": $('#timeOrderSelect').val()
        },
        success: function () {
            $("#" + ordersImpUid).show();
            $("#" + ordersImpUid + "timeOrder").hide();

            // var fcmData = {
            //     "to": token,
            //     "notification": {
            //         "body": $('#timeOrderSelect').val() + "분 후 음식이 완료됩니다!",
            //         "title": "소소한 부엌"
            //     }
            // };
            //
            // $.ajax({
            //     url: "https://fcm.googleapis.com/fcm/send",
            //     type: "POST",
            //     dataType: "json",
            //     contentType: "application/json",
            //     headers: {
            //         "Authorization": "key=AAAAo-oxD4w:APA91bEhjqW5Se6u7KyIGvjpm6rHkqbXHpPz_oGXZ4n2i7yAWraJLdGoZM021LVUDKJ4EZJH7nMCllV6XI4O2frslfLM9cIP1Vl12Q1SJnDx97PM-wDx79fZ4AxL1ssO9xr7pHvVqu2C"
            //     },
            //     data: JSON.stringify(fcmData)
            // });
        }, error: function () {
            alert("오류가 발생하였습니다.");
            location.href = "/admin/orderList?className=pas";
        }
    });
}


function getMemberProfile(memberSq, uid, place) { //멤버 주문 수정
    location.href = "/admin/order/changeDetail?memberSq=" + memberSq + "&uid=" + uid + "&place=" + place;
}


function connect() {
    var socket = new SockJS('/user/websocket');
    stompClient = Stomp.over(socket);
    // SockJS와 stomp client를 통해 연결을 시도.
    stompClient.connect({}, function (frame) {
        // console.log('Connected: ' + frame);
        stompClient.subscribe('/sendAdminMessage/OrderChat', function (chat) {
            showOrder(JSON.parse(chat.body));
        });
    });
}

function kioskSubscribe() { //키오스크
    var socket = new SockJS('/user/websocket');
    stompClient = Stomp.over(socket);
    // SockJS와 stomp client를 통해 연결을 시도.
    stompClient.connect({}, function (frame) {
        // console.log('Connected: ' + frame);
        stompClient.subscribe('/sendAdminMessage/kiosk/order', function (chat) {
            var JsonData = JSON.parse(chat.body);
            console.log(JsonData);
            showOrderByKiosk(JsonData);
        });
    });
}

function showOrderByKiosk(JsonData) { //키오스크 실시간 주문

    try {

        if (JsonData.orderDetailEntityList.length != 0) {


            sound();
            Toastify({
                text: "키오스크에서 주문이 들어왔습니다.",
                duration: 3600000, //3000 -> 3초 //즉 3600000 -> 1시간
                close: true,
                gravity: "bottom",
                position: "right",
                backgroundColor: "#e78b72",
                // destination: "/admin/order/changeDetail?memberSq=" + chat.memberSq + "&uid=" + chat.ordersImpUid + "&place=pas"
            }).showToast();


            var viewMessage = '<div class="col">\n' +
                '                        <div class="card h-100">\n' +
                '                            <div class="card-body">\n' +
                '                                <h5 class="card-title" style="margin-bottom: 20px;"\n' +
                '                                  >kiosk(' + JsonData.orderNumber + ')</h5>\n' +
                '                                <p class="card-text"></p>\n' +
                '                            </div>\n' +
                '                            <ul class="list-group list-group-flush">';

            var menuMessage = '';


            $(JsonData.orderDetailEntityList).each(function () { //mainMenu

                menuMessage += '<li class="list-group-item">\n' +
                    '                   <div class="d-flex bd-highlight">\n' +
                    '                        <p class="me-auto" style="margin: 0;">' + this.orderMenuName + '</p>\n' +
                    '                        <p class="" style="margin: 0;">' + this.orderDetailMenuSize + '개</p>\n' +
                    '                   </div>\n';


                if (this.orderDetailSideEntityList.length != 0) { //side

                    menuMessage += '<div style="display: flex; margin-top: 10px;">' +
                        '                                        <div style="width: 30%; height: auto; text-align: right;">\n' +
                        '                                            SIDE\n' +
                        '                                        </div>\n' +
                        '                                        <div style="width: 70%; height: auto;">';

                    $(this.orderDetailSideEntityList).each(function () {


                        menuMessage += ' <div class="d-flex bd-highlight" style="justify-content: right;"><!--  사이드메뉴-->\n' +
                            '                      <p class="" style="margin: 0;flex: 1;text-align: center">' + this.orderSideName + '</p>\n' +
                            '                       <p class="" style="margin: 0;text-align: right">' + this.orderSideSize + '개</p>\n' +
                            '            </div>';


                    });

                    menuMessage += '</div></div>';

                }

                menuMessage += '</li>';

            });

        }

        viewMessage += menuMessage;
        viewMessage += '</ul>';

        var placeStatus = "";
        if (JsonData.orderPlace == "inner") {
            placeStatus = "매장";
        } else {
            placeStatus = "포장";
        }

        viewMessage += '<div style="text-align: center; margin: 15px 0;">\n' +
            '              <div class="btn-group btn-group-sm" role="group" aria-label="Basic example">\n' +
            '                                    <button type="button" onclick=\'successKioskOrder("' + JsonData.orderTelegramNo + '")\' ' +
            '                                            class="btn btn-outline-success">\n' +
            '                                        주문 완료\n' +
            '                                    </button>\n' +
            '                                    <!--                                    <button type="button" class="btn btn-outline-dark"></button>-->\n' +
            '                                    <!--                                    <button type="button" class="btn btn-outline-danger">-->\n' +
            '                                    <!--                                        주문 취소-->\n' +
            '                                    <!--                                    </button>-->\n' +
            '             </div>\n' +
            '         </div>\n' +
            '         <div class="card-footer" style="padding: 1rem;">\n' +
            '               <small class="text-muted" style="color: #eb6547!important">총 금액 : ' + JsonData.orderTotalPrice + '</small><br>\n' +
            '               <small class="text-muted">주문 시각 : ' + JsonData.orderDate + '</small><br>\n' +
            '               <small class="text-muted">주문 번호 : ' + JsonData.orderTelegramNo + '</small><br>\n' +
            '               <small class="text-muted">결제 방식 : ' + JsonData.orderPayStatus + '</small><br>\n' +
            '               <small class="text-muted" style="color: #217aff!important;">' + placeStatus + '</small>\n' +
            '         </div>';

        viewMessage += '</div></div>';

        $('#kioskOrderListId').prepend(viewMessage);


    } catch (e) {
        return false;
    }

}

function showOrder(chat) {
    //알림음
    sound();
    //toast 알림
    Toastify({
        text: chat.orderName + "님의 주문이 들어왔습니다.",
        duration: 3600000, //3000 -> 3초 //즉 3600000 -> 1시간
        close: true,
        gravity: "bottom",
        position: "right",
        backgroundColor: "#4fbe87",
        destination: "/admin/order/changeDetail?memberSq=" + chat.memberSq + "&uid=" + chat.ordersImpUid + "&place=pas"
    }).showToast();

    var JsonMenuData = chat.ordersMenu;
    var menuSource = "<ul class='list-group list-group-flush'>";
    for (var i = 0; i < JsonMenuData.length; i++) {
        menuSource +=
            "<li class='list-group-item'>" +
            "<div class='d-flex bd-highlight'>" +
            "<p class='me-auto' style='margin: 0'>" + JsonMenuData[i].menuOrderName + "</p>" +
            "<p style='margin: 0'>메뉴 수량: " + JsonMenuData[i].menuOrderSize + "</p>" +
            "</div>" +
            "</li>";
    }

    menuSource += "</ul>";

    var name = 'timeOrder';

    var htmlSource =
        "<div class='col' id='" + chat.ordersMerchantUid + "'>" +
        "<div class='card h-100'>" +
        "<div class='card-body'>" +
        "<h5 class='card-title' style='margin-bottom: 20px;' onclick='getMemberProfile(\"" + chat.memberSq + "\",\"" + chat.ordersImpUid + "\"" +
        ",\"" + 'pas' + "\")'>" + chat.orderName + "님의 주문입니다</h5>" +
        "<p class='card-text'>" + chat.orderHelp + "</p>" +
        "</div>" + menuSource +
        "<div style=\"text-align: center; margin: 15px 0;\" id=\"" + chat.ordersImpUid + name + "\">\n" +
        "                                <div style=\"width: 30%; display: inline-block;\">\n" +
        "                                    <select class=\"form-select\" id=\"timeOrderSelect\">\n" +
        "                                        <option value=\"5\">5분</option>\n" +
        "                                        <option value=\"10\">10분</option>\n" +
        "                                        <option value=\"15\">15분</option>\n" +
        "                                        <option value=\"20\">20분</option>\n" +
        "                                        <option value=\"25\">25분</option>\n" +
        "                                        <option value=\"30\">30분</option>\n" +
        "                                        <option value=\"40\">40분</option>\n" +
        "                                        <option value=\"50\">50분</option>\n" +
        "                                        <option value=\"60\">60분</option>\n" +
        "                                        <option value=\"80\">80분</option>\n" +
        "                                    </select>\n" +
        "                                </div>\n" +
        "                                <div class=\"btn-group btn-group-sm\" role=\"group\"\n" +
        "                                     aria-label=\"Basic example\">\n" +
        "                                    <button type=\"button\" class=\"btn btn-outline-success\"\n" +
        "                                            onclick='OrderTime(\"" + chat.ordersImpUid + "\")'>" +
        "                                        주문 완료 시각\n" +
        "                                    </button>\n" +
        "                                </div>\n" +
        "                            </div>" +
        "<div style='text-align: center; margin: 15px 0;' id=\"" + chat.ordersImpUid + "\" class='nonButton'>" +
        "<div class='btn-group btn-group-sm' role='group' aria-label='Basic example'>" +
        "<button type='button' class='btn btn-outline-success' onclick='pasOrderSuccess(\"" + chat.memberSq + "\",\"" + chat.ordersMerchantUid + "\"" +
        ",\"" + chat.ordersImpUid + "\",\"" + chat.orders_id + "\")'>주문 완료</button>" +
        "<button type='button' class='btn btn-outline-dark'></button>\n" +
        "<button type='button' class='btn btn-outline-danger' onclick='pasOrderFail(\"" + chat.memberSq + "\",\"" + chat.ordersMerchantUid + "\"" +
        ",\"" + chat.ordersImpUid + "\",\"" + chat.orders_id + "\")'>주문 취소</button>" +
        "</div>" +
        "</div>" +
        "<div class=\"card-footer\" style=\"padding: 1rem;\">" +
        "<small class='text-muted' style='color: #eb6547!important'>총 금액 : " + chat.ordersTotalPrice + "</small>" + "<br>" +
        "<small class='text-muted'>주문 시간 : " + chat.orderDate + "</small>" + "<br>" +
        "<small class='text-muted'>주문 번호 : " + chat.ordersMerchantUid + "</small>" + "<br>" +
        "<small class='text-muted'>전화 번호 : " + chat.orderPhoneNumber + "</small>" + "<br>" +
        "<small class='text-muted'>주소 : " + chat.orderAddress + "</small>" +
        "</div>" +
        "</div>" +
        "</div>";

    // sssskkkk(" + chat.memberSq + "," + chat.ordersMerchantUid
    //     + "," + chat.ordersImpUid + "," + chat.orders_id + ")
    //html append

    $('#orderListId').prepend(htmlSource);
}


function successKioskOrder(orderTelegramNo) {

    console.log("삭제 orderTelegramNo : " + orderTelegramNo);

    if (confirm("주문을 완료하시겠습니까??")) {

        $.ajax({
            url: "/admin/kiosk/success/order",
            data: {"orderTelegramNo": orderTelegramNo},
            dataType: "JSON",
            type: "GET",
            success: function (data) {

                location.reload();

            }
        });

    } else {
        return false;
    }


}


function memberStartConnect() {
    var socket = new SockJS('/user/websocket');
    stompClient = Stomp.over(socket);
    // SockJS와 stomp client를 통해 연결을 시도.
    stompClient.connect({}, function (frame) {
        // console.log('Connected: ' + frame);
        stompClient.subscribe('/sendAdminMessage/memberCount', function (chat) {
            getMemberCountShow(JSON.parse(chat.body));
        });
    });
}

function getMemberCountShow(chat) {
    if (chat.orderSize === 0) {
        $('#activeMemberCount').text(chat.memberCount);
    } else {
        $('#activeMemberCount').text(chat.memberCount);

        Toastify({ //소소한부엌 확인안된거 가져옴
            text: chat.orderSize + "건의 주문이 들어왔습니다. by soso",
            duration: 3600000, //3000 -> 3초 //즉 3600000 -> 1시간
            close: true,
            gravity: "bottom",
            position: "right",
            backgroundColor: "#4fbe87",
            destination: "/admin/orderList?className=soso"  //지정 url로 이동
        }).showToast();
    }
}

function adminChatSend(memberSq, memberRole) {
    stompClient.send("/order/count", {}, JSON.stringify({
        'memberSq': memberSq,
        'role_name': memberRole,
        'loginActive': true
    }));
}

function adminLogout(memberSq, memberRole) {
    stompClient.send("/order/count", {}, JSON.stringify({
        'memberSq': memberSq,
        'role_name': memberRole,
        'loginActive': false
    }));
    stompClient.disconnect();
    document.logoutAdminForm.submit();
}

function startPas() { //앤빠스 주문시작
    stompClient.send("/order/start", {}, JSON.stringify({
        'act': 'true'
    }));
    alert("주문이 시작되었습니다.");
}

function disconnectPas() {
    stompClient.send("/order/start", {}, JSON.stringify({
        'act': 'false'
    }));
    alert("주문이 종료되었습니다.");
}


function startKiosk() {
    stompClient.send("/order/start/kiosk", {}, JSON.stringify({
        'act': 'true'
    }));
    alert("키오스크가 시작되었습니다.");
}

function disconnectKiosk() {
    stompClient.send("/order/start/kiosk", {}, JSON.stringify({
        'act': 'false'
    }));
    alert("키오스크가 종료되었습니다.");
}


//주문 에서 처리 ---------------------------------------------------


function orderSuccess(memberSq, ordersMerchantUid, ordersImpUid) { //주문 완료

}

function orderFail(memberSq, ordersMerchantUid, ordersImpUid) { //주문 취소

}
