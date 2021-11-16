function sosoAddMenu() {
    if ($('#menuSosoName').val() == "") {
        alert("메뉴 이름을 적어주세요.");
        return false;
    } else if ($('#menuSosoPrice').val() == "") {
        alert("가격을 적어주세요.");
        return false;
    } else {
        $('#soso_menu_form').submit();
    }
}