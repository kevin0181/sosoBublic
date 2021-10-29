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

    @Column(name = "menu_sold_out")
    private boolean menuSoldOut;

    @Column(name = "menu_enable")
    private boolean menuEnable;

    @Column(name = "menu_today")
    private boolean menuToday;

    @ManyToOne
    @JoinColumn(name = "menu_category_sq", insertable = false, updatable = false)
    private MenuCategoryDTO menuCategoryDTO;

    @OneToMany
    @JoinColumn(name = "menu_sq")
    private List<ImgDTO> menu_img_sq = new ArrayList<>();

//    @OneToMany
//    @JoinColumn(name = "menu_sq")
//    private List<OrderDTO> orderDTOList = new ArrayList<>();


    public MenuDTO(Long menuSq, String menuName, Long menuCategorySq, String menu_contant,
                   int menu_price, boolean menu_sold_out, boolean menu_enable, boolean menuToday, List<ImgDTO> menu_img_sq) {
        this.menuSq = menuSq;
        this.menuName = menuName;
        this.menuCategorySq = menuCategorySq;
        this.menu_contant = menu_contant;
        this.menu_price = menu_price;
        this.menuSoldOut = menu_sold_out;
        this.menuEnable = menu_enable;
        this.menuToday = menuToday;
        this.menu_img_sq = menu_img_sq;
    }
}
