var stompClient = null;

function connect() {
    var socket = new SockJS('/user/websocket');
    stompClient = Stomp.over(socket);
    // SockJS와 stomp client를 통해 연결을 시도.
    stompClient.connect({}, function (frame) {
        // console.log('Connected: ' + frame);
        stompClient.subscribe('/sendAdminMessage/OrderChat', function (chat) {
            showChat(JSON.parse(chat.body));
        });
    });
}


function showChat(chat) {
    Toastify({
        text: chat.orderName + "님의 주문이 들어왔습니다.",
        duration: 3600000, //3000 -> 3초 //즉 3600000 -> 1시간
        close: true,
        gravity: "bottom",
        position: "right",
        backgroundColor: "#4fbe87",
        destination: "/admin/orderList/OrderSingleBoard?className=order&orderImp=" + chat.ordersImpUid  //지정 url로 이동
    }).showToast();
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

        Toastify({
            text: chat.orderSize + "건의 주문이 더 들어왔습니다.",
            duration: 3600000, //3000 -> 3초 //즉 3600000 -> 1시간
            close: true,
            gravity: "bottom",
            position: "right",
            backgroundColor: "#4fbe87",
            destination: "/admin/orderList?className=order"  //지정 url로 이동
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
    document.logoutAdminForm.submit();
}