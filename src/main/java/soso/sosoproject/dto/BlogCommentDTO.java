package soso.sosoproject.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "blog_comment")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id") // 추가
public class BlogCommentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blogCommentSq;

    @Column(name = "blog_sq")
    private Long blogSq;

    @Column(name = "member_sq")
    private Long memberSq;

    @Column(name = "blog_comment_date")
    private String blogCommentDate;


    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "member_sq", insertable = false, updatable = false)
    private MemberDTO member_sq;

//    @ManyToOne
//    @JoinColumn(name = "blog_sq", insertable = false, updatable = false)
//    private BlogDTO blogDTO;

}
