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
    } else {
        Toastify({
            text: chat.orderName + "님의 주문이 들어왔습니다.",
            duration: 3600000, //3000 -> 3초 //즉 3600000 -> 1시간
            close: true,
            gravity: "bottom",
            position: "right",
            backgroundColor: "#4fbe87",
            destination: "/admin/orderList/OrderSingleBoard?className=pas&orderImp=" + chat.ordersImpUid  //지정 url로 이동
        }).showToast();
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