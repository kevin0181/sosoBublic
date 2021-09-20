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
            },
        });
        alert("변경되었습니다.");
        location.href = "/admin/add-menu";
    }
}