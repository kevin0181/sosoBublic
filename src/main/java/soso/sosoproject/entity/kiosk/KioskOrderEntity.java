package soso.sosoproject.entity.kiosk;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

}
