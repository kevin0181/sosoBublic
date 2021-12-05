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

    //알림음
    // const audio = document.getElementById('audio_play');
    // audio.autoplay = false;
    // audio.play();
    // audio.load();
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


    var htmlSource =
        "<div class='col' id='" + chat.ordersMerchantUid + "'>" +
        "<div class='card h-100'>" +
        "<div class='card-body'>" +
        "<h5 class='card-title' style='margin-bottom: 20px;' onclick='getMemberProfile(\"" + chat.memberSq + "\",\"" + chat.ordersImpUid + "\"" +
        ",\"" + 'pas' + "\")'>" + chat.orderName + "님의 주문입니다</h5>" +
        "<p class='card-text'>" + chat.orderHelp + "</p>" +
        "</div>" + menuSource +
        "<div style='text-align: center; margin: 15px 0;'>" +
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


//주문 에서 처리 ---------------------------------------------------

function getMemberProfile(memberSq, uid, place) { //멤버 주문 수정
    location.href = "/admin/order/changeDetail?memberSq=" + memberSq + "&uid=" + uid + "&place=" + place;
}

function orderSuccess(memberSq, ordersMerchantUid, ordersImpUid) { //주문 완료

}

function orderFail(memberSq, ordersMerchantUid, ordersImpUid) { //주문 취소

}
