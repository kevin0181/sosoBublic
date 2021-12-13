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
            },
            beforeSend: function () {
                $('#loading_var').show();
            }, success: function () {
                alert("카테고리가 추가되었습니다.");
                location.href = "/admin/Blog";
            }, error: function () {
                alert("카테고리가 추가되었습니다. 에러가 발생하였을 경우 관리자에게 문의주세요.");
                location.href = "/admin/Blog";
            }
        });

    }
}

var blogCategorySq;

function changeBlogCategory(blogCategorySq) {

    var result = prompt("변경할 카테고리를 입력해주세요.");

    if (!result) {
        alert("빈칸을 입력해주세요.");
        return false;
    } else {
        $.ajax({
            type: "GET",
            url: "/admin/blog/blog-category",
            dataType: "json",
            data: {
                "categoryName": result,
                "categoryId": blogCategorySq,
                "condition": "change"
            }, beforeSend: function () {
                $('#loading_var').show();
            }, success: function () {
                alert("카테고리가 변경되었습니다.");
                location.href = "/admin/Blog";
            }, error: function () {
                alert("카테고리가 변경되었습니다. 에러가 발생하였을 경우 관리자에게 문의주세요.");
                location.href = "/admin/Blog";
            }
        });
    }
}

function deleteBlogCategory(blogCategorySq) {

    var result = confirm("삭제하시겠습니까?");

    if (result) {
        $.ajax({
            type: "GET",
            url: "/admin/blog/blog-category",
            dataType: "json",
            data: {
                "categoryId": blogCategorySq,
                "condition": "delete"
            },
            beforeSend: function () {
                $('#loading_var').show();
            }, success: function () {
                alert("카테고리가 삭제되었습니다.");
                location.href = "/admin/Blog";
            }, error: function () {
                alert("카테고리가 삭제되었습니다. 에러가 발생했을경우 관리자에게 문의해주세요.");
                location.href = "/admin/Blog";
            }
        });

    } else {
        alert("삭제가 취소되었습니다.");
        location.href = "/admin/Blog";
    }


}


function changeActiveView(blogSq, active) {
    $.ajax({
        type: "GET",
        url: "/admin/blog/blogList/changeActive",
        dataType: "json",
        data: {
            "active": active,
            "blogSq": blogSq
        }
    });
    setTimeout(function () {
        location.href = "/admin/Blog";
    }, 200);
}

//블로그 삭제
function deleteBlog() {
    var blogCheck = [];
    $("input:checkbox[name='blogSqCheck']:checked").each(function () {
        blogCheck.push($(this).val());     // 체크된 것만 값을 뽑아서 배열에 push
    });
    if (isEmptyArr(blogCheck)) {
        alert("삭제할 메뉴를 체크해주세요.");
        return false;
    } else {
        $.ajax({
            type: "GET",
            url: "/admin/blog/deleteBlog",
            dataType: "json",
            data: {
                "blogCheck": blogCheck,
            },
            beforeSend: function () {
                $('#loading_var').show();
            }
        });
        setTimeout(function () {
            alert("블로그가 삭제되었습니다.");
            location.href = "/admin/Blog";
        }, 1000);
    }
}

//배열 빈값 체크
function isEmptyArr(arr) {
    if (Array.isArray(arr) && arr.length === 0) {
        return true;
    }
    return false;
}

