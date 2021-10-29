package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderGetJsonDTO {
    private Long memberSq;
    private String orderAddress;
    private String orderPhoneNumber;
    private String orderHelp;
    private boolean orderEnable;
    private boolean orderPlace;
}
