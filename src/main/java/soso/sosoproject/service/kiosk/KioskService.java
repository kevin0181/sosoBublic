package soso.sosoproject.service.kiosk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.kiosk.KioskMenuDTO;
import soso.sosoproject.dto.kiosk.KioskOrderDTO;
import soso.sosoproject.entity.kiosk.KioskOrderDetailEntity;
import soso.sosoproject.entity.kiosk.KioskOrderDetailSideEntity;
import soso.sosoproject.entity.kiosk.KioskOrderEntity;
import soso.sosoproject.repository.kiosk.KioskOrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class KioskService {

    @Autowired
    private KioskOrderRepository kioskOrderRepository;

    public KioskOrderEntity orderSave(List<KioskMenuDTO> kioskMenuDTOList, String totalPrice, String placeStatus, KioskOrderDTO kioskOrderDTO) {

        KioskOrderEntity kioskOrderEntity = new KioskOrderEntity();
        List<KioskOrderDetailEntity> kioskOrderDetailEntityList = new ArrayList<>();
        List<KioskOrderDetailSideEntity> kioskOrderDetailSideEntityList = new ArrayList<>();

        //-------주문 번호 및 날짜 총 가격 넣음-------
        kioskOrderEntity.setOrderPlace(kioskOrderDTO.getOrderPlace()); //주문 장소
        kioskOrderEntity.setOrderDate(kioskOrderDTO.getOrderDate()); //주문 시각
        kioskOrderEntity.setOrderEnable(false); //주문 상태
        kioskOrderEntity.setOrderTelegramNo(kioskOrderDTO.getOrderTelegramNo()); //주문 아이디
        kioskOrderEntity.setOrderTotalPrice(kioskOrderDTO.getOrderTotalPrice()); //주문 총 금액
        //------------------------------------------------------------------------------

        for (int i = 0; i < kioskMenuDTOList.size(); i++) {

            KioskOrderDetailEntity kioskOrderDetailEntity = new KioskOrderDetailEntity();

            kioskOrderDetailEntity.setMenuSq(kioskMenuDTOList.get(i).getMenuSq()); //메뉴 아이디
            kioskOrderDetailEntity.setOrderMenuName(kioskMenuDTOList.get(i).getMenuName()); //메뉴 이름
            kioskOrderDetailEntity.setOrderDetailMenuPrice(kioskMenuDTOList.get(i).getMenuPrice()); //메뉴 가격
            kioskOrderDetailEntity.setOrderDetailMenuSize(kioskMenuDTOList.get(i).getSize()); //메뉴 사이즈

            kioskOrderDetailEntityList.add(kioskOrderDetailEntity);

            if (kioskMenuDTOList.get(i).getAddSide().size() != 0) {


                for (int j = 0; j < kioskMenuDTOList.get(i).getAddSide().size(); j++) {

                    KioskOrderDetailSideEntity kioskOrderDetailSideEntity = new KioskOrderDetailSideEntity();

                    kioskOrderDetailSideEntity.setSideSq(kioskMenuDTOList.get(i).getAddSide().get(j).getSideSq()); //사이드 아이디
                    kioskOrderDetailSideEntity.setOrderSideName(kioskMenuDTOList.get(i).getAddSide().get(j).getSideName()); //사이드 이름
                    kioskOrderDetailSideEntity.setOrderSidePrice(kioskMenuDTOList.get(i).getAddSide().get(j).getSidePrice()); //사이드 가격
                    kioskOrderDetailSideEntity.setOrderSideSize(kioskMenuDTOList.get(i).getAddSide().get(j).getSideSize()); //사이드 사이즈


                    kioskOrderDetailSideEntityList.add(kioskOrderDetailSideEntity);

                }

                kioskOrderDetailEntityList.get(i).setKioskOrderDetailSideEntityList(kioskOrderDetailSideEntityList);

            }


        }

        kioskOrderEntity.setKioskOrderDetailEntityList(kioskOrderDetailEntityList);

        kioskOrderRepository.save(kioskOrderEntity);

        return kioskOrderEntity;
    }

}
