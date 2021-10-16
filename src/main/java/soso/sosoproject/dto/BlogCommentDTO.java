package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "blog_comment")
public class BlogCommentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_sq")
    private Long blog_sq;

    @Column(name = "member_email")
    private Long memberEmail;

    @Column(name = "comment")
    private Long comment;

    @ManyToOne
    @JoinColumn(name = "member_email", insertable = false, updatable = false)
    private MemberDTO member_email;

    @ManyToOne
    @JoinColumn(name = "blog_sq", insertable = false, updatable = false)
    private BlogDTO blogDTO;

}
