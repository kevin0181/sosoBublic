package soso.sosoproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "menu")
public class MenuDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_sq")
    private Long menuSq;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_category_sq")
    private Long menuCategorySq;

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

    @OneToMany
    @JoinColumn(name = "menu_sq", insertable = false, updatable = false)
    private List<ImgDTO> menu_img_sq = new ArrayList<>();

    public MenuDTO(Long menu_sq, String menu_name, Long menu_category_sq, String menu_contant,
                   int menu_price, boolean menu_sold_out, boolean menu_enable, boolean menu_today) {
        this.menuSq = menu_sq;
        this.menuName = menu_name;
        this.menuCategorySq = menu_category_sq;
        this.menu_contant = menu_contant;
        this.menu_price = menu_price;
        this.menu_sold_out = menu_sold_out;
        this.menu_enable = menu_enable;
        this.menu_today = menu_today;
    }

}
