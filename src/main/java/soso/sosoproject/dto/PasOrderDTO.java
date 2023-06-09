package soso.sosoproject.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Entity(name = "order_pas")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id") // 추가
public class PasOrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orders_id;

    @NotNull
    @Column(name = "member_sq")
    private Long memberSq;

    @NotNull
    @Length(max = 64)
    @Column(name = "orders_address")
    private String orderAddress;

    @NotNull
    @Length(max = 11)
    @Column(name = "orders_phonenumber")
    private String orderPhoneNumber;

    @Length(max = 500)
    @Column(name = "orders_help")
    private String orderHelp;

    @Column(name = "orders_enable")
    private boolean orderEnable;

    @NotNull
    @Column(name = "orders_date")
    private String orderDate;

    @Column(name = "orders_time")
    private String ordersTime;

    @NotNull
    @Length(max = 20)
    @Column(name = "orders_name")
    private String orderName;

    @Column(name = "orders_totalprice")
    private String ordersTotalPrice;

    @Column(name = "orders_imp_uid")
    private String ordersImpUid;

    @Column(name = "orders_save")
    private boolean ordersSave;

    @Column(name = "orders_merchant_uid")
    private String ordersMerchantUid;

    @Column(name = "orders_policy")
    private boolean ordersPolicy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "orders_id")
    private List<PasOrdersDetailDTO> ordersMenu;

    @ManyToOne
    @JoinColumn(name = "member_sq", insertable = false, updatable = false)
    private MemberDTO member_sq;

}
