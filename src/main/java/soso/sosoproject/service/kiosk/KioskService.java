package soso.sosoproject.service.kiosk;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.kiosk.KioskMenuDTO;
import soso.sosoproject.dto.kiosk.KioskOrderDTO;
import soso.sosoproject.entity.kiosk.KioskOrderDetailEntity;
import soso.sosoproject.entity.kiosk.KioskOrderDetailSideEntity;
import soso.sosoproject.entity.kiosk.KioskOrderEntity;
import soso.sosoproject.repository.kiosk.KioskOrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KioskService {

    @Autowired
    private KioskOrderRepository kioskOrderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public KioskOrderEntity orderSave(List<KioskMenuDTO> kioskMenuDTOList, KioskOrderDTO kioskOrderDTO) {

        KioskOrderEntity kioskOrderEntity = new KioskOrderEntity();
        List<KioskOrderDetailEntity> kioskOrderDetailEntityList = new ArrayList<>();
        List<KioskOrderDetailSideEntity> kioskOrderDetailSideEntityList = new ArrayList<>();

        //-------주문 번호 및 날짜 총 가격 넣음-------
        kioskOrderEntity.setOrderPlace(kioskOrderDTO.getOrderPlace()); //주문 장소
        kioskOrderEntity.setOrderDate(kioskOrderDTO.getOrderDate()); //주문 시각
        kioskOrderEntity.setOrderEnable(false); //주문 상태
        kioskOrderEntity.setOrderTelegramNo(kioskOrderDTO.getOrderTelegramNo()); //주문 아이디
        kioskOrderEntity.setOrderTotalPrice(kioskOrderDTO.getOrderTotalPrice()); //주문 총 금액
        kioskOrderEntity.setOrderPayStatus(kioskOrderDTO.getOrderPayStatus()); //주문 현금 or 카드
        kioskOrderEntity.setOrderTelegramNo(kioskOrderDTO.getOrderTelegramNo()); //주문 전문
        kioskOrderEntity.setOrderApprovalNo(kioskOrderDTO.getOrderApprovalNo());
        kioskOrderEntity.setOrderTradeUniqueNo(kioskOrderDTO.getOrderTradeUniqueNo());
        kioskOrderEntity.setOrderTradeTime(kioskOrderDTO.getOrderTradeTime());
        kioskOrderEntity.setOrderNumber(kioskOrderDTO.getOrderNumber());

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

    public List<KioskOrderDTO> getAllOrder() {


        List<KioskOrderEntity> kioskOrderEntityList = kioskOrderRepository.findAllByOrderEnable(false); // 완료 안된 주문 가져옴
        List<KioskOrderDTO> kioskOrderDTOList = kioskOrderEntityList.stream().map(kioskOrderEntity -> modelMapper.map(kioskOrderEntity, KioskOrderDTO.class)).collect(Collectors.toList());


//        for (int i = 0; i < kioskOrderDTOList.size(); i++) {
//
//
//            for (int j = 0; j < kioskOrderDTOList.get(i).getKioskOrderDetailEntityList().size(); j++) {
//
//                kioskOrderDTOList.get(i).getKioskOrderDetailEntityList().get(j).setKioskOrderEntity(null);
//
//            }
//
//
//        }


        return kioskOrderDTOList;
    }
}
