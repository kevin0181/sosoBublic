package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "orders_detail")
public class OrdersDetailDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menu_order_sq;

    @Column(name = "orders_id")
    private Long ordersId;

    @Column(name = "member_sq")
    private Long memberSq;

    @Column(name = "menu_sq")
    private Long menuSq;

    @Column(name = "menu_order_name")
    private String menuOrderName;

    @Column(name = "menu_order_size")
    private int menuOrderSize;


    @ManyToOne
    @JoinColumn(name = "member_sq", insertable = false, updatable = false)
    private MemberDTO member_sq;


    @ManyToOne
    @JoinColumn(name = "menu_sq", insertable = false, updatable = false)
    private MenuDTO menu_sq;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "orders_id", insertable = false, updatable = false)
//    private OrderDTO orderDTO;

}
