package soso.sosoproject.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "menu_category")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id") // 추가
public class MenuCategoryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_sq;

    @Column(nullable = false, length = 80, name = "category_name")
    private String categoryName;

    @OneToMany
    @JoinColumn(name = "menu_category_sq")
    private List<PasMenuDTO> menuList = new ArrayList<>();

}
