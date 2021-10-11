var menuSq;
var menuName;

function menuClick(menuSq, menuName) {

    $('#smallOrderText').remove();


    if ($('#menu' + menuSq).hasClass("click_white") === true) {
        $('#menu' + menuSq).removeClass("click_white");
        $('#menu' + menuSq).addClass("click_green");
        $('#menuForm').append("<div class='col-md-4 menuColDiv" + menuSq + "' style='display: inline-block'>" + "<input type='hidden' id='menuInput" + menuSq + "' name='menuSq' value=" + menuSq + ">" +
            "<p name='menuName'id='menuName" + menuSq + "' style='display: inline-block;font-size: 13px;font-weight: 300;' data-value=" + menuName + ">" + menuName + "</p>" +
            "<input type='number'id='menuNumber" + menuSq + "' min='0' style='border-color: #eeeeee;padding: 0.575rem 0.75rem;color: #999999;border: 1px solid #ced4da;' class='numberClass' name='menuSize' value='1'>"
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