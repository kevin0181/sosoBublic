package soso.sosoproject.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Column(name = "orders_address")
    private String orderAddress;

    @Column(name = "orders_phonenumber")
    private String orderPhoneNumber;

    @Column(name = "orders_help")
    private String orderHelp;

    @Column(name = "orders_enable")
    private boolean orderEnable;

    @Column(name = "orders_place")
    private String orderPlace;

    @Column(name = "orders_date")
    private String orderDate;

    @Column(name = "orders_name")
    private String orderName;

    @Column(name = "orders_totalprice")
    private String ordersTotalPrice;

    @Column(name = "orders_imp_uid")
    private String ordersImpUid;

    @Column(name = "orders_save")
    private boolean ordersSave;

    @Column(name = "orders_merchant_uid")
    private boolean ordersMerchantUid;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "orders_id")
    @JsonIgnore
    private List<OrdersDetailDTO> ordersMenu;

    @ManyToOne
    @JoinColumn(name = "member_sq", insertable = false, updatable = false)
    @JsonIgnore
    private MemberDTO member_sq;

}
