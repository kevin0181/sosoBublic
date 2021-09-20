package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "menu_category")
public class CategoryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_sq;

    @Column(nullable = false, length = 80, name = "category_name")
    private String category_name;

}
