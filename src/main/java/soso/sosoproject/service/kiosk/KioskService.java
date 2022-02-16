package soso.sosoproject.service.kiosk;

import org.springframework.stereotype.Service;
import soso.sosoproject.dto.kiosk.KioskMenuDTO;
import soso.sosoproject.dto.kiosk.KioskOrderDTO;
import soso.sosoproject.entity.kiosk.KioskOrderEntity;

import java.util.List;

@Service
public class KioskService {

    public KioskOrderEntity orderSave(List<KioskMenuDTO> kioskMenuDTOList, String totalPrice, String placeStatus, KioskOrderDTO kioskOrderDTO) {

        KioskOrderEntity kioskOrderEntity = new KioskOrderEntity();


        return null;
    }

}
