package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "blog_category")
public class BlogCategoryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long category_sq;

    @Column(name = "blog_category_name")
    private String blogCategoryName;

    @OneToMany
    @JoinColumn(name = "blog_category_sq")
    private List<BlogDTO> blogList = new ArrayList<>();

}
