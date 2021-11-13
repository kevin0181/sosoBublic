package soso.sosoproject.dto;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "menu_soso")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id") // 추가
public class SosoMenuDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menu_order_sq;

    @NotNull
    @Column(name = "menu_soso_name")
    private Long menuSosoName;

    @NotNull
    @Column(name = "menu_soso_price")
    private Long menuSosoPrice;
}
