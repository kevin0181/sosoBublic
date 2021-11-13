package soso.sosoproject.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.repository.SosoOrderRepository;

import java.util.List;

@Service
public class SosoOrderService {

    @Autowired
    private SosoOrderRepository sosoOrderRepository;

    public void saveSosoOrder(SosoOrderDTO sosoOrderDTO) {
        sosoOrderRepository.save(sosoOrderDTO);
    }

    public List<SosoOrderDTO> findAllPlaceAndEnableOrder() {
        return sosoOrderRepository.findAllByOrderEnableOrderByOrderDateDesc(false);
    }
}
