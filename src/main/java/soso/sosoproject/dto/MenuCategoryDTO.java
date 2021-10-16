package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "menu_category")
public class MenuCategoryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_sq;

    @Column(nullable = false, length = 80, name = "category_name")
    private String category_name;

    @OneToMany
    @JoinColumn(name = "menu_category_sq")
    private List<MenuDTO> menuList = new ArrayList<>();

}
