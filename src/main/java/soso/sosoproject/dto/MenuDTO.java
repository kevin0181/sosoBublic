package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "menu")
public class MenuDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menu_sq;

    @Column
    private String menu_name;

    @Column
    private Long menu_category_sq;

    @Column
    private String menu_contant;

    @Column
    private int menu_price;

    @Column
    private boolean menu_sold_out;

    @Column
    private boolean menu_enable;

    @Column
    private boolean menu_today;

    @ManyToOne
    @JoinColumn(name = "menu_category_sq", insertable = false, updatable = false)
    private CategoryDTO categoryDTO;

}
