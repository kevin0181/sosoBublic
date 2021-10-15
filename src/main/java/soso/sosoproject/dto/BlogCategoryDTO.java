package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "blog_category")
public class BlogCategoryDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogCategorySq;

    @Column(name = "blog_category_name")
    private Long blogCategoryName;

}
