package soso.sosoproject.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "orders_detail_pas")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id") // 추가
public class PasOrdersDetailDTO {

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
    @JsonIgnore
    private MemberDTO member_sq;

    @ManyToOne
    @JoinColumn(name = "menu_sq", insertable = false, updatable = false)
    @JsonIgnore
    private PasMenuDTO menu_sq;

    @ManyToOne
    @JoinColumn(name = "orders_id", insertable = false, updatable = false)
    private PasOrderDTO pasOrderDTO;

}
