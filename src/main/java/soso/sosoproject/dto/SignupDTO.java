package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;
import soso.sosoproject.dto.pk.MemberTablePk;

import javax.persistence.*;
import javax.validation.constraints.*;

@Setter
@Getter
@Entity(name = "member")
@IdClass(MemberTablePk.class)
public class SignupDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_sq;

    @Id
    @Column(nullable = false, length = 80)
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Size(min = 5, max = 80, message = "5 ~ 80자 아래로 적어주세요.")
    private String member_email;

    @Column(nullable = false, length = 16)
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String member_password;

    @Column(nullable = false, length = 35)
    @NotEmpty(message = "이름을 입력해주세요.")
    private String member_name;

    @Column(nullable = false, length = 300)
    @NotEmpty(message = "주소를 입력해주세요.")
    @Size(min = 10, max = 300, message = "주소 형식에 맞게 작성해주세요.")

    private String member_address;

    @Column(nullable = false, length = 12)
    @NotEmpty(message = "번호를 입력해주세요.")
    @Size(min = 5, max = 12, message = "형식에 맞는 번호를 적어주세요.")
    private String member_phonenumber;

    @Column
    private Long member_role_sq;

}
