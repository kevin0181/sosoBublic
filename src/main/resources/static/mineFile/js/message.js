var stompClient = null;

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


function showOrder(chat) {
    console.log(chat);
    if (chat.orderPlace === "soso") {
        Toastify({
            text: chat.orderName + "님의 주문이 들어왔습니다. by soso",
            duration: 3600000, //3000 -> 3초 //즉 3600000 -> 1시간
            close: true,
            gravity: "bottom",
            position: "right",
            backgroundColor: "#4fbe87",
            destination: "/admin/orderList/OrderSingleBoard?className=soso&orderImp=" + chat.ordersImpUid  //지정 url로 이동
        }).showToast();
    } else if (chat.orderPlace === "pas") {
        if (chat.message === "error-404") {

        } else {
            //toast 알림
            Toastify({
                text: chat.orderName + "님의 주문이 들어왔습니다.",
                duration: 3600000, //3000 -> 3초 //즉 3600000 -> 1시간
                close: true,
                gravity: "bottom",
                position: "right",
                backgroundColor: "#4fbe87",
                destination: "/admin/orderList/OrderSingleBoard?className=pas&orderImp=" + chat.ordersImpUid  //지정 url로 이동
            }).showToast();

            //html append
            $('#orderListId').prepend("<div class=\"col\">\n" +
                "                        <div class=\"card h-100\">\n" +
                "                            <div class=\"card-body\">\n" +
                "                                <h5 class=\"card-title\" style=\"margin-bottom: 20px;\">" + chat.orderName + "님의 주문입니다</h5>\n" +
                "                                <p class=\"card-text\">" + chat.orderHelp + "</p>\n" +
                "                            </div>\n" +
                "                            <ul class=\"list-group list-group-flush\">\n" +
                "                                <li class=\"list-group-item\">\n" +
                "                                    <div class=\"d-flex bd-highlight\">\n" +
                "                                        <p class=\"me-auto p-2\" style=\"margin: 0\">시킨 메뉴\n" +
                "                                            이름</p>\n" +
                "                                        <p class=\"p-2\" style=\"margin: 0\">메뉴\n" +
                "                                            수량</p>\n" +
                "                                    </div>\n" +
                "                                </li>\n" +
                "                            </ul>\n" +
                "                            <div style=\"text-align: center; margin: 15px 0;\">\n" +
                "                                <div class=\"btn-group btn-group-sm\" role=\"group\"\n" +
                "                                     aria-label=\"Basic example\">\n" +
                "                                    <button type=\"button\" class=\"btn btn-outline-success\">주문 완료</button>\n" +
                "                                    <button type=\"button\" class=\"btn btn-outline-dark\"></button>\n" +
                "                                    <button type=\"button\" class=\"btn btn-outline-danger\">주문 취소</button>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                            <div class=\"card-footer\">\n" +
                "                                <small class=\"text-muted\">" + chat.orderDate + "</small>\n" +
                "                            </div>\n" +
                "                        </div>\n" +
                "                    </div>");
        }
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
            text: chat.orderSize + "건의 주문이 더 들어왔습니다. by soso",
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