var category_seq;


function addCategory() {
    var category_form = $("#category_form").serialize();
    $.ajax({
        type: "GET",
        url: "/menu/add-category",
        dataType: "json",
        data: category_form
    });
    alert("카테고리가 추가되었습니다.");
    location.href = "/admin/add-menu";
}


// 카테고리 변경
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

// 카테고리 삭제
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

// 메뉴 추가
function addMenu() {
    var menu_form = $("#menu_id_form").serialize();

    $.ajax({
        type: "POST",
        url: "/menu/add-menu",
        dataType: "json",
        data: menu_form
    });
    alert("메뉴가 추가되었습니다.");
    location.href = "/admin/add-menu";
}

//메뉴 삭제
function deleteMenu() {
    var menuCheck = [];
    $("input:checkbox[name='menuCheck']:checked").each(function () {
        menuCheck.push($(this).val());     // 체크된 것만 값을 뽑아서 배열에 push
    });
    $.ajax({
        type: "POST",
        url: "/menu/delete-menu",
        dataType: "json",
        data: {
            "menuCheck": menuCheck,
            "condition": "delete"
        }
    });
    alert("메뉴가 삭제되었습니다.");
    location.href = "/admin/add-menu";
}