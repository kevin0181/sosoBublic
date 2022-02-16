package soso.sosoproject.dto.kiosk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KioskOrderDTO {

    private Long order_sq;

    private String orderId;

    private String orderTotalPrice;

    private String orderDate;

    private String orderPlace;

    private boolean orderEnable = false;

    private List<KioskOrderDetailDTO> kioskOrderDetailEntityList;
}
