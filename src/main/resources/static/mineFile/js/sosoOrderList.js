function orderSuccess(memberSq, ordersMerchantUid, ordersImpUid, orders_id) {

    if (ordersMerchantUid != null && ordersImpUid != null) {
        //일반 주문
        if (confirm("주문을 완료하시겠습니까?") == true) {
            //주문 완료
            $.ajax({
                type: "GET",
                url: "/admin/orderList/orderComplte",
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
                url: "/admin/orderList/orderComplte",
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

function orderFail(memberSq, ordersMerchantUid, ordersImpUid, orders_id) {
    if (ordersMerchantUid != null && ordersImpUid != null) {
        //일반 주문
        if (confirm("주문을 추소하시겠습니까?") == true) {
            //주문 취소
            $.ajax({
                type: "GET",
                url: "/admin/orderList/orderDelete",
                dataType: "json",
                data: {
                    'memberSq': memberSq,
                    'orders_id': orders_id,
                    'ordersMerchantUid': ordersMerchantUid,
                    'ordersImpUid': ordersImpUid
                },
                success: function (result) {

                    if (result) {
                        // $.ajax({
                        //     type: "GET",
                        //     url: "/admin/orderList/orderDelete",
                        //     dataType: "json",
                        //     data: {
                        //         'memberSq': memberSq,
                        //         'orders_id': orders_id,
                        //     }, success: function (result) {
                        //         if (result) {
                        alert("주문취소를 완료하였습니다.");

                        location.href = "/admin/orderList?className=soso";
                        return false;
                        // }
                        // }
                        // });

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
                url: "/admin/orderList/orderDelete",
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