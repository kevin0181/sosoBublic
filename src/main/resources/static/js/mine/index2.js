var orderDTO = new Object();

var menuArray = [];


var menuIdArray = [];

function menuClick(menuSq, menuName) {

    $('#smallOrderText').remove();


    if ($('#menu' + menuSq).hasClass("click_white") === true) {
        $('#menu' + menuSq).removeClass("click_white");
        $('#menu' + menuSq).addClass("click_green");
        $('#menuForm').append("<div class='menuColDiv" + menuSq + " row col-md-12' style='text-align: left'>" + "<input type='hidden' id='menuInput" + menuSq + "' name='menuSq' value=" + menuSq + ">" +
            "<p name='menuName'id='menuName" + menuSq + "' style='font-size: 14px;padding-top: 10px;' class='col' value=" + menuName + ">" + menuName + "</p>" +
            "<input type='number'id='menuNumber" + menuSq + "' min='0' class='form-control col' style='' name='menuSize' value='1'>"
            + "</div>");
        menuIdArray.push(menuSq);
    } else if ($('#menu' + menuSq).hasClass("click_green") === true) {
        $('#menu' + menuSq).removeClass("click_green");
        $('#menu' + menuSq).addClass("click_white");
        $('#menuInput' + menuSq).remove();
        $('#menuName' + menuSq).remove();
        $('#menuNumber' + menuSq).remove();
        $('.menuColDiv' + menuSq).remove();

        for (let i = 0; i < menuIdArray.length; i++) {
            if (menuIdArray[i] === menuSq) {
                menuIdArray.splice(i, 1);
                i--;
            }
        }
    }
}

function orderAlert(memberSq) {

    if (memberSq == null) {
        alert("로그인을 해주세요.");
        location.href = "/user/account/login";
        return false;
    }
    if ($('#orderName').val() == "") {
        alert("이름을 입력해주세요.");
        return false;
    }
    if ($('#orderAddress').val() == "") {
        alert("주소를 입력해주세요.");
        return false;
    }
    if ($('#orderNumber').val() == "") {
        alert("전화번호를 입력해주세요.");
        return false;
    }
    if ($('#orderPlace').val() == '없음') {
        alert("가게를 골라주세요.");
        return false;
    }
    if (menuIdArray.length == 0) {
        alert("메뉴를 골라주세요.");
        return false;
    }

    for (var i = 0; i < menuIdArray.length; i++) {
        if ($('#menuNumber' + menuIdArray[i]).val() == 0) {
            alert("알수없는 값이 들어왔습니다. 최소 수량 주문은 1개 입니다.");
            return false;
            break;
        }
    }
    $('#orderModal').show();
    for (var i = 0; i < menuIdArray.length; i++) {
        if (i == menuIdArray.length - 1) {
            $('#modalOrderBody').append("<span>" + $('#menuName' + menuIdArray[i]).text() + " </span>");
        } else {
            $('#modalOrderBody').append("<span>" + $('#menuName' + menuIdArray[i]).text() + ", </span>");
        }
    }
    $('#modalOrderBody').append("<span> 를 주문하셨습니다. 결제 방식을 선택해주세요.</span>");
}


//메뉴주문
function orderKakaoPay(memberSq, memberEmail) {

    connect();

    var ammountResult = 0;
    var userName;

    var formdata = new FormData();
    formdata.append("memberSq", memberSq);
    formdata.append("orderName", $('#orderName').val());
    formdata.append("orderAddress", $('#orderAddress').val());
    formdata.append("orderPhoneNumber", $('#orderNumber').val());
    formdata.append("orderHelp", $('#orderHelp').val());
    formdata.append("orderEnable", true);
    formdata.append("orderPlace", $('#orderPlace').val());
    formdata.append("member_sq", memberSq);
    formdata.append("orderDate", $('#orderDate').val());


    orderDTO.ordersMenu = new Object();
    for (var i = 0; i < menuIdArray.length; i++) {

        formdata.append("ordersMenu[" + i + "].memberSq", memberSq);
        formdata.append("ordersMenu[" + i + "].menuSq", $('#menuInput' + menuIdArray[i]).val());
        formdata.append("ordersMenu[" + i + "].menuOrderName", $('#menuName' + menuIdArray[i]).text());
        formdata.append("ordersMenu[" + i + "].menuOrderSize", $('#menuNumber' + menuIdArray[i]).val());
        formdata.append("ordersMenu[" + i + "].member_sq", memberSq);
        formdata.append("ordersMenu[" + i + "].menu_sq", $('#menuInput' + menuIdArray[i]).val());

    }

    $.ajax({
        url: "/user/orderMenu/pay/ammount",
        type: "post",
        dataType: "json",
        contentType: false,
        processData: false,
        data: formdata,
        success: function (data) {

            IMP.init('imp76725859');
            IMP.request_pay({
                pg: 'kakao',
                pay_method: 'card',
                merchant_uid: 'merchant_' + new Date().getTime(),
                name: 'soso', //결제창에서 보여질 이름
                amount: data, //실제 결제되는 가격
                buyer_email: memberEmail,
                buyer_name: $('#orderName').val(),
                buyer_tel: $('#orderNumber').val(),
                buyer_addr: $('#orderAddress').val(),
            }, function (rsp) {

                if (rsp.success) {
                    formdata.append("ordersImpUid", rsp.imp_uid);
                    formdata.append("ordersTotalPrice", data);
                    $.ajax({
                        url: "/user/order/menu",
                        type: "post",
                        dataType: "json",
                        contentType: false,
                        processData: false,
                        data: formdata,
                        success: function (data) {
                            if (data) {
                                alert("성공적으로 주문이 되었습니다.");
                                sendChat(memberSq, rsp.imp_uid, userName);
                                location.href = "/user/index";
                            } else {
                                alert("주문에 실패하였습니다. 관리자에게 문의 부탁드립니다.");
                                location.href = "/user/index";
                            }
                        }
                    });
                } else {
                    var msg = '결제에 실패하였습니다.';
                    msg += '에러내용 : ' + rsp.error_msg;
                    alert(msg);
                }
            });
        }

    });

    /* pg: <-
'kakao':카카오페이,
html5_inicis':이니시스(웹표준결제)
'nice':나이스페이
'jtnet':제이티넷
'uplus':LG유플러스
'danal':다날
'payco':페이코
'syrup':시럽페이
'paypal':페이팔
*/


}

//웹소켓 연결
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

function sendChat(memberSq, imp_uid, userName) {
    stompClient.send("/order/chat", {}, JSON.stringify({
        'memberSq': memberSq,
        'ordersImpUid': imp_uid,
        'orderName': $('#orderName').val()
    }));
}


function orderAlertClose() {
    $('#orderModal').hide();
    $('#modalOrderBody').empty();
}