package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "blog_img")
public class BlogImgDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogImgSq;

    @Column(name = "blog_sq")
    private Long blogSq;

    @Column(name = "blog_img_path")
    private Long blogImgPath;

    @Column(name = "blog_img_name")
    private Long blogImgName;

    @Column(name = "blog_img_date")
    private Long blogImgDate;


}
