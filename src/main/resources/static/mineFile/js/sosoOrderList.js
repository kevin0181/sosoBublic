function sosoOrderSuccess(memberSq, ordersMerchantUid, ordersImpUid, orders_id) {

    if (ordersMerchantUid != null && ordersImpUid != null) {
        //일반 주문
        if (confirm("주문을 완료하시겠습니까?") == true) {
            //주문 완료
            $.ajax({
                type: "GET",
                url: "/admin/orderList/sosoOrderComplte",
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
                        location.href = "/admin/orderList?className=soso";
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
                url: "/admin/orderList/sosoOrderComplte",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                },
                success: function (result) {

                    if (result) {
                        alert("주문을 완료하였습니다.");
                        location.href = "/admin/orderList?className=soso";
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

function sosoOrderFail(memberSq, ordersMerchantUid, ordersImpUid, orders_id) {
    if (ordersMerchantUid != null && ordersImpUid != null) {
        //일반 주문
        if (confirm("주문을 추소하시겠습니까?") == true) {
            //주문 취소
            $.ajax({
                type: "GET",
                url: "/admin/orderList/sosoOrderDelete",
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
                        location.href = "/admin/orderList?className=soso";
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
                url: "/admin/orderList/sosoOrderDelete",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                },
                success: function (result) {

                    if (result) {
                        alert("주문취소를 완료하였습니다.");
                        location.href = "/admin/orderList?className=soso";
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


function sosoOrderFailAll(memberSq, ordersMerchantUid, ordersImpUid, orders_id) {
    if (ordersMerchantUid != null && ordersImpUid != null) {
        //일반 주문
        if (confirm("주문을 추소하시겠습니까?") == true) {
            //주문 취소
            $.ajax({
                type: "GET",
                url: "/admin/orderList/sosoOrderDelete",
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
                        location.href = "/admin/All/OrderList?className=sosoList";
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
                url: "/admin/orderList/sosoOrderDelete",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                },
                success: function (result) {

                    if (result) {
                        alert("주문취소를 완료하였습니다.");
                        location.href = "/admin/All/OrderList?className=sosoList";
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


$('#OrderAllCheckSoso').click(function () {
    if ($("input:checkbox[id='OrderAllCheckSoso']").prop("checked")) {
        $("input[id='orderCheckSoso']").prop("checked", true);
    } else {
        $("input[id='orderCheckSoso']").prop("checked", false);
    }
});

//soso 전체 주문 리스트 삭제
function deleteSosoOrderList() {
    var sosoCheck = [];
    $("input:checkbox[id='orderCheckSoso']:checked").each(function () {
        sosoCheck.push($(this).val());     // 체크된 것만 값을 뽑아서 배열에 push
    });
    console.log(sosoCheck);
    if (isEmptyArr(sosoCheck)) {
        alert("삭제할 메뉴를 체크해주세요.");
        return false;
    } else {
        $.ajax({
            type: "GET",
            url: "/admin/order/delete-order/soso",
            dataType: "json",
            data: {
                "sosoCheck": sosoCheck,
            }, beforeSend: function () {
                $('#loading_var').show();
            },
            success: function (data) {
                alert("주문이 삭제되었습니다.");
                location.href = "/admin/All/OrderList?className=sosoList";
            },
            error: function (data) {
                if (data) {
                    alert("주문이 삭제되었습니다.");
                    location.href = "/admin/All/OrderList?className=sosoList";
                }
                alert("주문이 삭제를 실패하였습니다.");
                location.href = "/admin/All/OrderList?className=sosoList";
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


