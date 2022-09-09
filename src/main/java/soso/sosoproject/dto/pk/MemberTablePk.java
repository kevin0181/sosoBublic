package soso.sosoproject.dto.pk;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;

@Data
public class MemberTablePk implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_sq;
    private String member_email;
}
