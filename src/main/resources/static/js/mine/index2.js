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
        menuIdArray.pop(menuSq);
    }
}

//메뉴주문
function orderMenu(memberSq) {

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
        if ($('#menuNumber' + menuIdArray[i]).val() == 0) {
            alert("알수없는 값이 들어왔습니다. 최소 수량 주문은 1개 입니다.");
            break;
            location.href = "/user/index";
        }

        formdata.append("ordersMenu[" + i + "].memberSq", memberSq);
        formdata.append("ordersMenu[" + i + "].menuSq", $('#menuInput' + menuIdArray[i]).val());
        formdata.append("ordersMenu[" + i + "].menuOrderName", $('#menuName' + menuIdArray[i]).text());
        formdata.append("ordersMenu[" + i + "].menuOrderSize", $('#menuNumber' + menuIdArray[i]).val());
        formdata.append("ordersMenu[" + i + "].member_sq", memberSq);
        formdata.append("ordersMenu[" + i + "].menu_sq", $('#menuInput' + menuIdArray[i]).val());
    }

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
            } else {
                alert("주문에 실패하였습니다. 관리자에게 문의 부탁드립니다.");
            }
        }
    });

}