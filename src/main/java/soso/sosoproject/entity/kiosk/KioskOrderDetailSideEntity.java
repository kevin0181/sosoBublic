package soso.sosoproject.entity.kiosk;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "kiosk_order_detail_side")
public class KioskOrderDetailSideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_detail_side_sq;

    @Column(name = "order_detail_sq")
    private Long orderDetailSq;

    @Column(name = "side_sq")
    private Long sideSq;

    @Column(name = "order_side_name")
    private String orderSideName;

    @Column(name = "order_side_size")
    private int orderSideSize;

    @Column(name = "order_side_price")
    private String orderSidePrice;

    @ManyToOne
    @JoinColumn(name = "order_detail_sq")
    private KioskOrderDetailEntity kioskOrderDetailEntity;

}
