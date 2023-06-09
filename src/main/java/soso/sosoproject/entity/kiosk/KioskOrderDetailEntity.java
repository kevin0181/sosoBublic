package soso.sosoproject.entity.kiosk;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "kiosk_order_detail")
public class KioskOrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_sq")
    private Long orderDetailSq;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_sq", insertable = false, updatable = false)
    private KioskOrderEntity kioskOrderEntity;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_detail_sq")
    private List<KioskOrderDetailSideEntity> orderDetailSideEntityList;
}
