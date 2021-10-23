package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

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
    private String blogDate;

    @Column(name = "blog_contant")
    private String blogContant;

    @Column(name = "blog_view_size")
    private int blogViewSize;

    @Column(name = "blog_top_img_name")
    private String blogTopImgName;

    @Column(name = "blog_top_img_path")
    private String blogTopImgPath;

    @Column(name = "blog_img_keyname")
    private String blogImgKeyname;

    @Column(name = "blog_view_active")
    private boolean blogViewActive;

    @ManyToOne
    @JoinColumn(name = "blog_category_sq", insertable = false, updatable = false)
    private BlogCategoryDTO blogCategoryDTO;

    @OneToMany
    @JoinColumn(name = "blog_sq")
    private List<BlogImgDTO> blogImg_sq = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "blog_sq")
    private List<BlogCommentDTO> blogCommentDTOList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_sq", insertable = false, updatable = false)
    private MemberDTO memberDTO;

}
