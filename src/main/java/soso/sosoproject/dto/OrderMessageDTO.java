package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderMessageDTO {
    private Long memberSq;
    private String ordersImpUid;
    private String orderName;
    private String role_name;
    private String orderPlace;
}
