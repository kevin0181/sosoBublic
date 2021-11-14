package soso.sosoproject.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.SosoMenuDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.repository.SosoMenuRepository;
import soso.sosoproject.repository.SosoOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SosoOrderService {

    @Autowired
    private SosoOrderRepository sosoOrderRepository;

    @Autowired
    private SosoMenuRepository sosoMenuRepository;

    public void saveSosoOrder(SosoOrderDTO sosoOrderDTO) {
        sosoOrderRepository.save(sosoOrderDTO);
    }

    public List<SosoOrderDTO> findAllPlaceAndEnableOrder() {
        return sosoOrderRepository.findAllByOrderEnableOrderByOrderDateDesc(false);
    }

    public int getTotalService(Long menu_order_sq, int userSize) {

        Optional<SosoMenuDTO> optionalSosoMenuDTO = sosoMenuRepository.findById(menu_order_sq);
        SosoMenuDTO sosoMenuDTO = optionalSosoMenuDTO.get();

        return userSize * sosoMenuDTO.getMenuSosoPrice();
    }
}
