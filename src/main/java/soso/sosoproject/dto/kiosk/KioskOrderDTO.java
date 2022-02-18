package soso.sosoproject.dto.kiosk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KioskOrderDTO {

    private Long order_sq;

    private String orderTotalPrice;

    private String orderDate;

    private String orderPlace;

    private boolean orderEnable = false;

    private String orderPayStatus;

    private String orderTelegramNo;

    private String orderTradeTime;

    private String orderApprovalNo;

    private String orderTradeUniqueNo;

    private String orderNumber;

    private List<KioskOrderDetailDTO> orderDetailEntityList;
}
