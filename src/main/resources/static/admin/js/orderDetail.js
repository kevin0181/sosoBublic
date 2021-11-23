function sosoChangeOrderDetailButton() {
    if ($('#sosoChangeTime').val() == "") {
        alert("변경할 날짜를 지정해주세요.");
        return false;
    } else {
        $('#sosoChangeForm').submit();
    }
}

function pasChangeOrderDetailButton() {
    // if ($('#pasChangeTime').val() == "") {
    //     alert("변경할 날짜를 지정해주세요.");
    //     return false;
    // } else {
    //     $('#pasChangeForm').submit();
    // }
    alert("앤 빠스떼우는 주문내역 수정이 불가능합니다. (변경을 원하시면 관리자에게 문의해주세요.)");
    return false;
}