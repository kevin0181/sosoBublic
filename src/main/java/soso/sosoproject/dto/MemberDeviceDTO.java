package soso.sosoproject.dto;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "member_device")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id") // 추가
public class MemberDeviceDTO {


    @Id
    private Long memberSq;

    @Column(name = "device_number")
    private String deviceNumber;

    @OneToOne
    @JoinColumn(name = "member_sq")
    @JsonIgnore
    private MemberDTO memberDTO;

}
