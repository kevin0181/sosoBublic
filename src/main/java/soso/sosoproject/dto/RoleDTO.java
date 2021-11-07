package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_sq;

    @Column(name = "role_name")
    private String roleName;

//    @ManyToMany(mappedBy = "role")
//    private Set<MemberDTO> userModel = new HashSet<>();

}
