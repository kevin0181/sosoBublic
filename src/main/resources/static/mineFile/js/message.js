$(document).ready(function () {
    connect();
});

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
    alert(chat.orderName + "님의 주문이 들어왔습니다.");
    console.log(chat);
}
