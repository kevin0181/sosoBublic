package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Oauth2DTO {
    private String memberEmail;
    private String memberName;
    private String memberAddress;
    private String memberPhonenumber;
    private String socialId;
    private boolean policy;
}
