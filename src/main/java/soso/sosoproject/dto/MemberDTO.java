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
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Size(min = 5, max = 80, message = "5 ~ 80자 아래로 적어주세요.")
    private String memberEmail;

    @Column(nullable = false, length = 16, name = "member_password")
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @Column(nullable = false, length = 35, name = "member_name")
    @NotEmpty(message = "이름을 입력해주세요.")
    private String memberName;

    @Column(nullable = false, length = 300, name = "member_address")
    @NotEmpty(message = "주소를 입력해주세요.")
    @Size(min = 10, max = 300, message = "주소 형식에 맞게 작성해주세요.")

    private String memberAddress;

    @Column(nullable = false, length = 12, name = "member_phonenumber")
    @NotEmpty(message = "번호를 입력해주세요.")
    @Size(min = 5, max = 12, message = "형식에 맞는 번호를 적어주세요.")
    private String memberPhonenumber;


    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "member_role",
            joinColumns = @JoinColumn(name = "member_sq"),
            inverseJoinColumns = @JoinColumn(name = "role_sq")
    )
    private Set<RoleDTO> role = new HashSet<>();


}
