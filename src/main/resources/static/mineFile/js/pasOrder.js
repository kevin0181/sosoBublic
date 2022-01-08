function pasOrderSuccess(memberSq, ordersMerchantUid, ordersImpUid, orders_id) {

    if (ordersMerchantUid != null && ordersImpUid != null) {
        //일반 주문
        if (confirm("주문을 완료하시겠습니까?") == true) {
            //주문 완료
            $.ajax({
                type: "GET",
                url: "/admin/orderList/pasOrderComplte",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                    'ordersMerchantUid': ordersMerchantUid,
                    'ordersImpUid': ordersImpUid
                },
                success: function (result) {

                    if (result) {
                        alert("주문을 완료하였습니다.");
                        location.href = "/admin/orderList?className=pas";
                        return false;
                    } else {
                        alert("주문완료에 실패하였습니다.(관리자에게 문의해주세요)");
                        return false;
                    }

                }
            });

        } else {
            return false;
        }
    } else {
        //예약 주문
        if (confirm("주문을 완료하시겠습니까?") == true) {
            //주문 완료
            $.ajax({
                type: "GET",
                url: "/admin/orderList/pasOrderComplte",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                },
                success: function (result) {

                    if (result) {
                        alert("주문을 완료하였습니다.");
                        location.href = "/admin/orderList?className=pas";
                        return false;
                    } else {
                        alert("주문완료에 실패하였습니다.(관리자에게 문의해주세요)");
                        return false;
                    }

                }
            });
        } else {
            return false;
        }
    }

}

function pasOrderFail(memberSq, ordersMerchantUid, ordersImpUid, orders_id) {
    if (ordersMerchantUid != null && ordersImpUid != null) {
        //일반 주문
        if (confirm("주문을 추소하시겠습니까?") == true) {
            //주문 취소
            $.ajax({
                type: "GET",
                url: "/admin/orderList/pasOrderDelete",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                    'ordersMerchantUid': ordersMerchantUid,
                    'ordersImpUid': ordersImpUid
                },
                success: function (result) {

                    if (result) {
                        alert("주문취소를 완료하였습니다.");
                        location.href = "/admin/orderList?className=pas";
                        return false;
                    } else {
                        alert("주문취소에 실패하였습니다.(관리자에게 문의해주세요)");
                        return false;
                    }

                }
            });

        } else {
            return false;
        }
    } else {
        //예약 주문
        if (confirm("주문을 취소하시겠습니까?") == true) {
            //주문 완료
            $.ajax({
                type: "GET",
                url: "/admin/orderList/pasOrderDelete",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                },
                success: function (result) {

                    if (result) {
                        alert("주문취소를 완료하였습니다.");
                        location.href = "/admin/orderList?className=pas";
                        return false;
                    } else {
                        alert("주문취소에 실패하였습니다.(관리자에게 문의해주세요)");
                        return false;
                    }

                }
            });
        } else {
            return false;
        }
    }
}


function pasOrderFailAll(memberSq, ordersMerchantUid, ordersImpUid, orders_id) {
    if (ordersMerchantUid != null && ordersImpUid != null) {
        //일반 주문
        if (confirm("주문을 추소하시겠습니까?") == true) {
            //주문 취소
            $.ajax({
                type: "GET",
                url: "/admin/orderList/pasOrderDelete",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                    'ordersMerchantUid': ordersMerchantUid,
                    'ordersImpUid': ordersImpUid
                },
                success: function (result) {

                    if (result) {
                        alert("주문취소를 완료하였습니다.");
                        location.href = "/admin/All/OrderList?className=pasList";
                        return false;
                    } else {
                        alert("주문취소에 실패하였습니다.(관리자에게 문의해주세요)");
                        return false;
                    }

                }
            });

        } else {
            return false;
        }
    } else {
        //예약 주문
        if (confirm("주문을 취소하시겠습니까?") == true) {
            //주문 완료
            $.ajax({
                type: "GET",
                url: "/admin/orderList/pasOrderDelete",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                },
                success: function (result) {

                    if (result) {
                        alert("주문취소를 완료하였습니다.");
                        location.href = "/admin/All/OrderList?className=pasList";
                        return false;
                    } else {
                        alert("주문취소에 실패하였습니다.(관리자에게 문의해주세요)");
                        return false;
                    }

                }
            });
        } else {
            return false;
        }
    }
}


$('#OrderAllCheckPas').click(function () {
    if ($("input:checkbox[id='OrderAllCheckPas']").prop("checked")) {
        $("input[id='orderCheckPas']").prop("checked", true);
    } else {
        $("input[id='orderCheckPas']").prop("checked", false);
    }
});


//pas 전체 주문 리스트 삭제
function deletePasOrderList() {
    var pasCheck = [];
    $("input:checkbox[id='orderCheckPas']:checked").each(function () {
        pasCheck.push($(this).val());     // 체크된 것만 값을 뽑아서 배열에 push
    });
    console.log(pasCheck);
    if (isEmptyArr(pasCheck)) {
        alert("삭제할 메뉴를 체크해주세요.");
        return false;
    } else {
        $.ajax({
            type: "GET",
            url: "/admin/order/delete-order/pas",
            dataType: "json",
            data: {
                "pasCheck": pasCheck,
            }, beforeSend: function () {
                $('#loading_var').show();
            },
            success: function (data) {
                alert("주문이 삭제되었습니다.");
                location.href = "/admin/All/OrderList?className=pasList";
            },
            error: function (data) {
                if (data) {
                    alert("주문이 삭제되었습니다.");
                    location.href = "/admin/All/OrderList?className=pasList";
                }
                alert("주문이 삭제를 실패하였습니다.");
                location.href = "/admin/All/OrderList?className=pasList";
            }
        });
    }
}


//배열 빈값 체크
function isEmptyArr(arr) {
    if (Array.isArray(arr) && arr.length === 0) {
        return true;
    }
    return false;
}


function pasSearchDate() {
    var pasDateCalender = $('#pasDateCalender').val();
    location.href = "/admin/All/OrderList?className=pasList&dateSize=" + pasDateCalender;
}

function pasChangeMonthOrderList(month) {
    var today = new Date();
    today.setHours(today.getHours() + 9);
    var year = today.toISOString().substring(0, 5);

    location.href = "/admin/All/OrderList?className=pasList&month=" + (year + $(month).val());
}