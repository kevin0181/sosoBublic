var category_seq;


function addCategory() {
    var category_form = $("#category_form").serialize();
    if (!$('#category_name').val()) {
        alert("빈칸을 입력해주세요.");
        return false;
    } else {
        $.ajax({
            type: "GET",
            url: "/menu/add-category",
            dataType: "json",
            data: category_form
        });
        alert("카테고리가 추가되었습니다.");
        location.href = "/admin/add-category";
    }
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
            url: "/admin/add-category",
            dataType: "json",
            data: {
                "className": "add-category",
                "condition": "change",
                "category_id": category_seq,
                "category_name": name
            }
        });
        alert("변경되었습니다.");
        location.href = "/admin/add-category";
    }
}

// 카테고리 삭제
function deleteCategory(category_seq) {
    $.ajax({
        type: "GET",
        url: "/admin/add-category",
        dataType: "json",
        data: {
            "className": "add-category",
            "condition": "delete",
            "category_id": category_seq
        }
    });
    alert("삭제되었습니다.");
    location.href = "/admin/add-category";
}

// 메뉴 추가
function addMenu() {
    var menu_form = $("#menu_id_form");
    var formData = new FormData(menu_form[0]);

    if (!$('#menu_name').val() || !$('#menu_price').val()) {
        alert("빈칸을 입력해주세요.");
        return false;
    } else {
        $.ajax({
            type: "POST",
            url: "/menu/add-menu",
            dataType: "json",
            processData: false,
            contentType: false,
            timeout: 20000000000,
            data: formData
        });
        alert("메뉴가 추가되었습니다.");
        location.href = "/admin/add-menu";
    }
}

//메뉴 삭제
function deleteMenu() {
    var menuCheck = [];
    $("input:checkbox[name='menuCheck']:checked").each(function () {
        menuCheck.push($(this).val());     // 체크된 것만 값을 뽑아서 배열에 push
    });
    if (isEmptyArr(menuCheck)) {
        alert("삭제할 메뉴를 체크해주세요.");
        return false;
    } else {
        $.ajax({
            type: "GET",
            url: "/menu/delete-menu",
            dataType: "json",
            data: {
                "menuCheck": menuCheck,
            }
        });
        alert("메뉴가 삭제되었습니다.");
        location.href = "/admin/add-menu";
    }
}

//배열 빈값 체크
function isEmptyArr(arr) {
    if (Array.isArray(arr) && arr.length === 0) {
        return true;
    }
    return false;
}


var menuSq;

//메뉴 변경
function changeMenu(menuSq) {
    var changeMenuform = $('#changeMenuModal' + menuSq);
    var formData = new FormData(changeMenuform[0]);
    if (!$('#changeMenuName' + menuSq).val() || !$('#changeMenuPrice' + menuSq).val()) {
        alert("빈칸을 입력해주세요.");
        return false;
    }

    $.ajax({
        type: "POST",
        url: "/menu/change-menu",
        dataType: "json",
        processData: false,
        contentType: false,
        data: formData
    });

    alert("메뉴가 수정되었습니다.");
    location.href = "/admin/add-menu";
}

//카테고리 검색
function searchCategoryJs() {
    $('#searchCategoryForm').submit();
}

//전체선택 해제
$('#listCheck').click(function () {
    if ($("#listCheck").is(':checked')) {
        $("input[name=menuCheck]").prop("checked", true);
    } else {
        $("input[name=menuCheck]").prop("checked", false);
    }
});