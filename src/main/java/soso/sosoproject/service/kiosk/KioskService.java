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

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public List<KioskOrderDTO> getFalseOrder() {

        List<KioskOrderEntity> kioskOrderEntityList = kioskOrderRepository.findAllByOrderEnable(false); // 완료 안된 주문 가져옴
        List<KioskOrderDTO> kioskOrderDTOList = kioskOrderEntityList.stream().map(kioskOrderEntity -> modelMapper.map(kioskOrderEntity, KioskOrderDTO.class)).collect(Collectors.toList());

        return kioskOrderDTOList;
    }

    public List<KioskOrderDTO> getAllOrder() {

        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDateFormat = simpleDateFormat.format(nowDate);

        List<KioskOrderEntity> kioskOrderEntityList = kioskOrderRepository.findAllByOrderDate(nowDateFormat); // 완료 안된 주문 가져옴
        List<KioskOrderDTO> kioskOrderDTOList = kioskOrderEntityList.stream().map(kioskOrderEntity -> modelMapper.map(kioskOrderEntity, KioskOrderDTO.class)).collect(Collectors.toList());

        return kioskOrderDTOList;
    }

    public void orderSaveData(KioskOrderEntity kioskOrderEntity) {
        kioskOrderRepository.save(kioskOrderEntity);
    }


    @Transactional
    public void successOrderService(String orderTelegramNo) {

        KioskOrderEntity kioskOrderEntity = kioskOrderRepository.findByOrderTelegramNo(orderTelegramNo);
        kioskOrderEntity.setOrderEnable(true);

        kioskOrderRepository.save(kioskOrderEntity);

    }


}
