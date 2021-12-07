function searchInstagramTag(tagName) {
    $.ajax({
        type: "get",
        url: "/user/get/tag",
        data: {
            "tagName": tagName
        },
        beforeSend: function () {
            $('#index_loading_var').show();
        },
        success: function (data) {
            $('#index_loading_var').hide();

            var JsonData = JSON.parse(data);

            console.log(JsonData);

        }
    });

}