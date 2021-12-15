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