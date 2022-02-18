package soso.sosoproject.entity.kiosk;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "kiosk_order")
public class KioskOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_sq;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_total_price")
    private String orderTotalPrice;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "order_place")
    private String orderPlace;

    @Column(name = "order_enable")
    private boolean orderEnable;

    @Column(name = "order_pay_status") //50
    private String orderPayStatus;

    @Column(name = "order_telegramno") //12 // 전문일련번호
    private String orderTelegramNo;

    @Column(name = "order_tradetime") //6 //거래일시
    private String orderTradeTime;

    @Column(name = "order_approvalno") //12 //승인번호
    private String orderApprovalNo;

    @Column(name = "order_tradeuniqueno") //20 //거래고유번호
    private String orderTradeUniqueNo;

    @Column(name = "order_number")
    private String orderNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_sq")
    private List<KioskOrderDetailEntity> orderDetailEntityList;

}
