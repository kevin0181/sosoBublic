function addBlogCategory() {
    var category_form = $("#blog_category_name").val();
    if (!$('#blog_category_name').val()) {
        alert("빈칸을 입력해주세요.");
        return false;
    } else {
        $.ajax({
            type: "GET",
            url: "/admin/blog/blog-category",
            dataType: "json",
            data: {
                "categoryName": category_form,
                "condition": "add"
            }
        });
        alert("카테고리가 추가되었습니다.");
        location.href = "/admin/Blog";
    }
}

var blogCategorySq;

function changeBlogCategory(blogCategorySq) {
    const reBlogCategory_name = $('#reBlogCategory_name' + blogCategorySq).val();
    if (!reBlogCategory_name) {
        alert("빈칸을 입력해주세요.");
        return false;
    } else {
        $.ajax({
            type: "GET",
            url: "/admin/blog/blog-category",
            dataType: "json",
            data: {
                "categoryName": reBlogCategory_name,
                "categoryId": blogCategorySq,
                "condition": "change"
            }
        });
        alert("카테고리가 변경되었습니다.");
        location.href = "/admin/Blog";
    }
}

function deleteBlogCategory(blogCategorySq) {
    $.ajax({
        type: "GET",
        url: "/admin/blog/blog-category",
        dataType: "json",
        data: {
            "categoryId": blogCategorySq,
            "condition": "delete"
        }
    });
    alert("카테고리가 삭제되었습니다.");
    location.href = "/admin/Blog";
}
