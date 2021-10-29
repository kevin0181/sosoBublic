var menuSq;
var menuName;

function menuClick(menuSq, menuName) {

    $('#smallOrderText').remove();


    if ($('#menu' + menuSq).hasClass("click_white") === true) {
        $('#menu' + menuSq).removeClass("click_white");
        $('#menu' + menuSq).addClass("click_green");
        $('#menuForm').append("<div class='menuColDiv" + menuSq + " row col-md-12' style='text-align: left'>" + "<input type='hidden' id='menuInput" + menuSq + "' name='menuSq' value=" + menuSq + ">" +
            "<p name='menuName'id='menuName" + menuSq + "' style='font-size: 14px;padding-top: 10px;' class='col' data-value=" + menuName + ">" + menuName + "</p>" +
            "<input type='number'id='menuNumber" + menuSq + "' min='0' class='form-control col' style='' name='menuSize' value='1'>"
            + "</div>");
    } else if ($('#menu' + menuSq).hasClass("click_green") === true) {
        $('#menu' + menuSq).removeClass("click_green");
        $('#menu' + menuSq).addClass("click_white");
        $('#menuInput' + menuSq).remove();
        $('#menuName' + menuSq).remove();
        $('#menuNumber' + menuSq).remove();
        $('.menuColDiv' + menuSq).remove();
    }
}