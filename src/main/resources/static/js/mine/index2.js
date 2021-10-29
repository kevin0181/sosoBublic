var menuObject = new Object();

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


    menuObject.memberSq = memberSq;
    menuObject.orderName = $('#orderName').val();
    menuObject.orderAddress = $('#orderAddress').val();
    menuObject.orderPhoneNumber = $('#orderNumber').val();
    menuObject.orderHelp = $('#orderHelp').val();
    menuObject.orderEnable = true;
    menuObject.orderPlace = $('#orderPlace').val();

    menuObject.ordersMenu = new Object();
    for (var i = 0; i < menuIdArray.length; i++) {
        var orderMenu = new Object();
        orderMenu.memberSq = memberSq;
        orderMenu.menuSq = $('#menuInput' + menuIdArray[i]).val();
        orderMenu.menuOrderName = $('#menuName' + menuIdArray[i]).text();
        if ($('#menuNumber' + menuIdArray[i]).val() == 0) {
            alert("알수없는 값이 들어왔습니다. 최소 수량 주문은 1개 입니다.");
            break;
            location.href = "/user/index";
        }
        orderMenu.menuOrderSize = $('#menuNumber' + menuIdArray[i]).val();

        menuArray.push(orderMenu);
    }

    menuObject.ordersMenu = menuArray;


    var orderDTO = {
        menuObject
    };

    var jsonData = JSON.stringify(orderDTO);
    console.log(orderDTO);

    $.ajax({
        url: "/user/order/menu",
        type: "post",
        dataType: "json",
        data: jsonData,
        success: function (data) {
            if (data) {
                alert("성공");
            }
        }
    });

}