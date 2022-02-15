package soso.sosoproject.dto.kiosk;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class KioskOrderDTO {

    private Long order_sq;

    private String orderId;

    private String orderTotalPrice;

    private String orderDate;

    private String orderPlace;

    private boolean orderEnable;

}
