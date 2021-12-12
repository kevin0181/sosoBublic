function searchInstagramTag(tagName) {

    $('#instagramSingleDiv').empty();

    var count = 0;
    $.ajax({
        type: "get",
        url: "/user/get/tag",
        data: {
            "tagName": tagName
        },
        beforeSend: function () {
            $('#review_section').hide();
            $('#index_loading_var').show();
        },
        success: function (data) {
            $('#review_section').show();
            $('#index_loading_var').hide();

            var JsonData = JSON.parse(data);

            console.log(JsonData.graphql.hashtag.edge_hashtag_to_media);

            count = JsonData.graphql.hashtag.edge_hashtag_to_media.count; //게시글 수량

            $('#countAllInstagramTag').text(count + "개의 게시글 보러가기");
            $("#countAllInstagramTag").attr("target", "_blank");
            $("#countAllInstagramTag").attr("href", "https://www.instagram.com/explore/tags/" + tagName + "/?max_id=1");

            apppendRevieSingleDiv(JsonData.graphql.hashtag.edge_hashtag_to_media);
        }
    });
}

function apppendRevieSingleDiv(JsonData) {

    const revSize = 4;
    console.log(JsonData.edges[0]);
    for (var i = 0; i < 4; i++) {
        var divAppend = " <div class=\"col-lg-3 col-md-6 col-sm-6 single-blog\">\n" +
            "                <div class=\"thumb\">\n" +
            "                    <img class=\"img-fluid\" src=\"" + JsonData.edges[i].node.display_url + "\" style=\"height: 250px;\" alt=\"인스타그램 사진\">\n" +
            // "</img>" +
            "                </div>\n" +
            "                <a>\n" +
            "                    <h4 style=\"margin-top: 15px;\"></h4>\n" +
            "                </a>\n" +
            "                <p>\n" +
            "                    <!--                    짧막한 말쓸수있음-->\n" +
            "                </p>\n" +
            "                <div class=\"meta-bottom d-flex justify-content-between\">\n" +
            "                    <p>1<span class=\"lnr lnr-heart\"></span></p>\n" +
            "                    <div style=\"display: inherit;\">\n" +
            "                        <span class=\"lnr lnr-bubble\"></span>\n" +
            "                        <p>\n" +
            "                            댓글수\n" +
            "                        </p>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>";
        $('#instagramSingleDiv').append(divAppend);
    }

}