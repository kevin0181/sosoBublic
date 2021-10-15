package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Setter
@Getter
@Entity(name = "blog")
public class BlogDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogSq;

    @Column(name = "blog_category_sq")
    private Long blogCategorySq;

    @Column(name = "member_sq")
    private Long memberSq;

    @Column(name = "blog_title", length = 200)
    private Long blogTitle;

    @Column(name = "blog_date")
    private Date blogDate;

    @Column(name = "blog_contant")
    private String blogContant;

    @Column(name = "blog_view_size")
    private String blogViewSize;
}
