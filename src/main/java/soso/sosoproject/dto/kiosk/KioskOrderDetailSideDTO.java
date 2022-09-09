package soso.sosoproject.dto.kiosk;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class KioskOrderDetailSideDTO {

    private Long orderDetailSideSq;

    private Long orderDetailSq;

    private Long sideSq;

    private String orderSideName;

    private int orderSideSize;

    private String orderSidePrice;

//    private KioskOrderDetailDTO kioskOrderDetailEntity;

}
