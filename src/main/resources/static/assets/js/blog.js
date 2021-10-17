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

//메뉴 삭제
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

