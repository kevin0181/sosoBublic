package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;
import soso.sosoproject.dto.pk.MemberTablePk;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity(name = "member")
//@IdClass(MemberTablePk.class)
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


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "member_sq"),
            inverseJoinColumns = @JoinColumn(name = "role_sq")
    )
    private Set<RoleDTO> role = new HashSet<>();


}
