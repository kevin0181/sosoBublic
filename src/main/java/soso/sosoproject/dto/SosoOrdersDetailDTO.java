package soso.sosoproject.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "orders_detail_soso")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id") // 추가
public class SosoOrdersDetailDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menu_order_detail_sq;

    @Column(name = "member_sq")
    private Long memberSq;

    @Column(name = "menu_order_size")
    private Long menuOrderSize;

    @Column(name = "order_soso_id")
    private Long orderSosoId;

    @Column(name = "menu_soso_sq")
    private Long menuSosoSq;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_soso_sq", insertable = false, updatable = false)
    private SosoMenuDTO sosoMenuDTO;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_soso_id", insertable = false, updatable = false)
    private SosoOrderDTO sosoOrderDTO;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_sq", insertable = false, updatable = false)
    private MemberDTO memberDTO;


}
