package soso.sosoproject.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity(name = "member")
//@IdClass(MemberTablePk.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id") // 추가
public class MemberDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_sq;

    @Column(nullable = false, length = 80, name = "member_email")
    private String memberEmail;

    @Column(nullable = false, length = 16, name = "member_password")
    private String password;

    @Column(nullable = false, length = 35, name = "member_name")
    private String memberName;

    @Column(nullable = false, length = 300, name = "member_address")
    private String memberAddress;

    @Column(nullable = false, length = 12, name = "member_phonenumber")
    private String memberPhonenumber;

    @Column(nullable = false, length = 10, name = "certification_number")
    private String certiNumber;

    @Column(nullable = false, name = "policy", updatable = false)
    private boolean policy;

    @Column(name = "social_id")
    private String socialId;


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "member_sq"),
            inverseJoinColumns = @JoinColumn(name = "role_sq")
    )
    private Set<RoleDTO> role = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_sq")
    @JsonIgnore
    private List<PasOrderDTO> pasOrderDTOList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_sq")
    @JsonIgnore
    private List<BlogDTO> blogDTOList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_sq")
    @JsonIgnore
    private List<BlogCommentDTO> blogCommentDTOList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_sq")
    @JsonIgnore
    private List<SosoOrderDTO> sosoOrderDTOS = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_sq")
    @JsonIgnore
    private List<SosoOrdersDetailDTO> sosoOrdersDetailDTOS = new ArrayList<>();
}
