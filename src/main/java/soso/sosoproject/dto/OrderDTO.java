package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "order")
public class OrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders_id;

    @Column(name = "member_sq")
    private Long memberSq;

    @Column(name = "order_address")
    private String orderAddress;

    @Column(name = "order_phoneNumber")
    private String orderPhoneNumber;

    @Column(name = "order_help")
    private String orderHelp;

    @Column(name = "order_enable")
    private boolean orderEnable;

    @Column(name = "order_place")
    private boolean orderPlace;

    @OneToMany
    @JoinColumn(name = "orders_id")
    private List<OrdersDetailDTO> ordersMenu = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_sq", insertable = false, updatable = false)
    private MemberDTO member_sq;

}
