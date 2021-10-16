package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "blog")
public class BlogDTO {

    @Id
    private Long blogSq;

    @Column(name = "blog_category_sq")
    private Long blogCategorySq;

    @Column(name = "member_sq")
    private Long memberSq;

    @Column(name = "blog_title", length = 200)
    private String blogTitle;

    @Column(name = "blog_date")
    private Date blogDate;

    @Column(name = "blog_contant")
    private String blogContant;

    @Column(name = "blog_view_size")
    private String blogViewSize;

    @ManyToOne
    @JoinColumn(name = "blog_category_sq", insertable = false, updatable = false)
    private BlogCategoryDTO blogCategoryDTO;

    @OneToMany
    @JoinColumn(name = "blog_sq")
    private List<BlogImgDTO> blogImg_sq = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "blog_sq")
    private List<BlogCommentDTO> blogCommentDTOList = new ArrayList<>();

}
