package soso.sosoproject.dto.kiosk;

import lombok.Getter;
import lombok.Setter;
import soso.sosoproject.entity.kiosk.KioskOrderEntity;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
public class KioskOrderDetailDTO {

    private Long order_detail_sq;

    private Long orderSq;

    private Long menuSq;

    private String orderMenuName;

    private int orderDetailMenuSize;

    private String orderDetailMenuPrice;

    private KioskOrderDTO kioskOrderEntity;

    private List<KioskOrderDetailSideDTO> kioskOrderDetailSideEntityList;

}
