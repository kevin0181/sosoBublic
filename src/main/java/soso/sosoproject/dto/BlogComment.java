package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "blog_comment")
public class BlogComment {

    @Column(name = "blog_sq")
    private Long blog_sq;

    @Column(name = "member_email")
    private Long memberEmail;

    @Column(name = "comment")
    private Long comment;

}
