var category_seq;

function changeCategory(category_seq) {
    const name = $('#reCategory_name' + category_seq).val();
    if (!name) {
        alert("빈칸을 입력해주세요.");
        return false;
    } else {
        $.ajax({
            type: "GET",
            url: "/admin/add-menu",
            dataType: "json",
            data: {
                "className": "add-menu",
                "condition": "change",
                "id": category_seq,
                "category_name": name
            }
        });
        alert("변경되었습니다.");
        location.href = "/admin/add-menu";
    }
}

function deleteCategory(category_seq) {
    $.ajax({
        type: "GET",
        url: "/admin/add-menu",
        dataType: "json",
        data: {
            "className": "add-menu",
            "condition": "delete",
            "id": category_seq
        }
    });
    alert("삭제되었습니다.");
    location.href = "/admin/add-menu";
}

function addMenu() {
    var menu_contant = $(".ql-editor");

    var menu_form = $("#menu_id_form").serialize();

    menu_form += "&menu_contant=" + menu_contant;

    $.ajax({
        type: "POST",
        url: "/menu/add-menu",
        dataType: "json",
        data: menu_form
    });
    alert("메뉴가 추가되었습니다.");
    location.href = "/admin/add-menu";
}