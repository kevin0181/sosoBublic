package soso.sosoproject.entity.kiosk;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "kiosk_order_detail")
public class KioskOrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_detail_sq;

    @Column(name = "order_sq")
    private Long orderSq;

    @Column(name = "menu_sq")
    private Long menuSq;

    @Column(name = "order_menu_name")
    private String orderMenuName;

    @Column(name = "order_detail_menu_size")
    private int orderDetailMenuSize;

    @Column(name = "order_detail_menu_price")
    private String orderDetailMenuPrice;

}
