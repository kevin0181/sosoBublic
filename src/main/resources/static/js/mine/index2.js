var orderDTO = new Object();

var menuArray = [];


var menuIdArray = [];


var startPasActive;


var stompClient = null;


function menuClick(menuSq, menuName, menuSoldOut) {

    if (menuSoldOut) {
        alert("품절된 메뉴입니다.");
        return false;
    }

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

function orderAlert(memberSq, memberEMail, memberRole) {

    if (memberSq == null) {
        alert("로그인을 해주세요.");
        location.href = "/user/account/login";
        return false;
    }

    if (!startPasActive) {
        alert("오픈 전 입니다. 불편을 드려 죄송합니다.");
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
    if ($('#pasOrderCheckbox').is(":checked") != true) {
        alert("주문 정보 활용 동의를 체크해주세요.");
        return false;
    }
    if (menuIdArray.length == 0) {
        alert("메뉴를 선택해주세요.");
        return false;
    }
    if ($('#selectPasPayVale').val() == "none") {
        alert("지불 방식을 선택해주세요.");
        return false;
    }
    for (var i = 0; i < menuIdArray.length; i++) {
        if ($('#menuNumber' + menuIdArray[i]).val() == 0) {
            alert("최소 수량 주문은 1개 입니다.");
            return false;
            break;
        }
    }
    if (!(/^[0-9]{2,3}[0-9]{3,4}[0-9]{4}/.test($('#orderNumber').val()))) {
        alert("전화번호 형식에 맞지 않습니다. - 을 제거하고 입력해주세요.");
        return false;
    }
    orderKakaoPay(memberSq, memberEMail, memberRole);
}


//메뉴주문
function orderKakaoPay(memberSq, memberEmail, memberRole) {

    // connect();

    var ammountResult = 0;
    var nowDate = new Date();
    var dateStr = timestamp();

    var formdata = new FormData();
    formdata.append("memberSq", memberSq);
    formdata.append("orderName", $('#orderName').val());
    formdata.append("orderAddress", $('#orderAddress').val());
    formdata.append("orderPhoneNumber", $('#orderNumber').val());
    formdata.append("orderHelp", $('#orderHelp').val());
    formdata.append("orderEnable", false);
    // formdata.append("orderPlace", 'pas'); //장소 중요 선택 없을 시 주문 불가.
    formdata.append("member_sq", memberSq);
    formdata.append("orderDate", dateStr);
    formdata.append("ordersPolicy", $('#pasOrderCheckbox').is(":checked"));

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
            if (data.error) {
                alert("입력값을 정확히 확인해주세요.");
                location.href = '/user/index';
            } else {
                IMP.init('imp76725859');
                IMP.request_pay({
                        pg: $('#selectPasPayVale').val(),
                        pay_method: 'card',
                        // merchant_uid: 'merchant_' + new Date().getTime(),
                        merchant_uid: data.uid,
                        name: '앤 빠스떼우', //결제창에서 보여질 이름
                        amount: data.totalPrice, //실제 결제되는 가격
                        buyer_email: memberEmail,
                        buyer_name: $('#orderName').val(),
                        buyer_tel: $('#orderNumber').val(),
                        buyer_addr: $('#orderAddress').val(),
                        // m_redirect_url: "http://soso-k.kro.kr/user/index"
                    },
                    function (rsp) {
                        if (rsp.success) {
                            formdata.append("ordersImpUid", rsp.imp_uid);
                            formdata.append("ordersTotalPrice", data.totalPrice);
                            formdata.append("ordersMerchantUid", rsp.merchant_uid);
                            $.ajax({
                                url: "/user/order/menu",
                                type: "post",
                                dataType: "json",
                                contentType: false,
                                processData: false,
                                data: formdata,
                                beforeSend: function () {
                                    $('#index_loading_var').show();
                                },
                                success: function (data) {
                                    if (data) {
                                        alert("성공적으로 주문이 되었습니다.");
                                        sendOrderChat(memberSq, rsp.imp_uid, memberRole);
                                        stompClient.disconnect();
                                        location.replace("/user/index")
                                    } else {
                                        alert("주문에 실패하였습니다.결제가 진행되었을 경우 관리자에게 문의 부탁드립니다.");
                                        location.replace("/user/index");
                                    }

                                }
                            });

                        } else {
                            var msg = '결제에 실패하였습니다.';
                            msg += '에러내용 : ' + rsp.error_msg;
                            alert(msg);
                            //DB에서도 처리해야함.

                            // $.ajax({
                            //     url: "/user/order/menu/cancle",
                            //     type: "GET",
                            //     dataType: "json",
                            //     data: {
                            //         'uid': rsp.merchant_uid
                            //     },
                            //     success: function (data) {
                            //         console.log(data);
                            //         if (data) {
                            //             console.log("메뉴 취소 완료");
                            //         } else {
                            //             alert("메뉴 주문 취소에 실패하였습니다. (관리자에게 문의바랍니다)");
                            //         }
                            //     }
                            // });

                            location.href = "/user/index";

                        }
                    }
                )
                ;
            }
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
            var JsonData = JSON.parse(chat.body);
            if (JsonData.message == "error-404") {
                alert("처리중 오류가 났습니다. 결제가 실행 된 경우 soso에게 문의해주세요.");
                location.href = "/user/index";
            }
        });
    });
}

function sendOrderChat(memberSq, imp_uid, memberRole) {
    stompClient.send("/order/chat", {}, JSON.stringify({
        // 'memberSq': memberSq,
        'ordersImpUid': imp_uid,
        // 'orderName': $('#orderName').val(),
        // 'role_name': memberRole,
        // 'orderPlace': 'pas'
    }));
}

function memberStartConnect() {
    var socket = new SockJS('/user/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // console.log('Connected: ' + frame);
        stompClient.subscribe('/sendAdminMessage/memberCount', function (chat) {
            var result = JSON.parse(chat.body);
            startPasActive = result.startPas;
            if (startPasActive) {
                $('#openId').text('오픈 중 입니다. 메뉴를 선택해주세요!');
            } else {
                $('#openId').text('오픈 전 입니다. 불편을 드려 죄송합니다.');
            }
        });
    });
}

function sendCount(memberSq, memberEMail, memberRole) {
    stompClient.send("/order/count", {}, JSON.stringify({
        'memberSq': memberSq,
        'memberEmail': memberEMail,
        'role_name': memberRole,
        'loginActive': true
    }));
}


function orderAlertClose() {
    $('#orderModal').hide();
    $('#modalOrderBody').empty();
}

function logoutActive(memberEMail, memberSq) { //로그아웃
    stompClient.send("/order/count", {}, JSON.stringify({
        'memberSq': memberSq,
        'loginActive': false
    }));
    stompClient.disconnect();
    document.logoutForm.submit();
}

function timestamp() {
    var today = new Date();
    today.setHours(today.getHours() + 9);
    return today.toISOString().replace('T', ' ').substring(0, 19);
}

