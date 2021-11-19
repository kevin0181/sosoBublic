package soso.sosoproject.service.order;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.dto.SosoMenuDTO;
import soso.sosoproject.dto.SosoOrderDTO;
import soso.sosoproject.repository.SosoMenuRepository;
import soso.sosoproject.repository.SosoOrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SosoOrderService {


    private IamportClient imIamportClient;

    public SosoOrderService() {
        // REST API 키와 REST API secret 를 아래처럼 순서대로 입력한다.
        this.imIamportClient =
                new IamportClient("1152819197412694",
                        "acffbee8c37f2492f2654739c30af6863c53e981f2488325703fb8d691f222814862c2ab7d67779a");
    }

    @Autowired
    private SosoOrderRepository sosoOrderRepository;

    @Autowired
    private SosoMenuRepository sosoMenuRepository;

    public void saveSosoOrder(SosoOrderDTO sosoOrderDTO) {
        sosoOrderRepository.save(sosoOrderDTO);
    }

    public List<SosoOrderDTO> findAllPlaceAndEnableOrder() {
        return sosoOrderRepository.findAllByOrderEnable(false);
    }

    public int getTotalService(Long menu_order_sq, int userSize) {

        Optional<SosoMenuDTO> optionalSosoMenuDTO = sosoMenuRepository.findById(menu_order_sq);
        SosoMenuDTO sosoMenuDTO = optionalSosoMenuDTO.get();

        return userSize * sosoMenuDTO.getMenuSosoPrice();
    }

    public List<SosoOrderDTO> findAllPlaceOrder() {
        return sosoOrderRepository.findAll();
    }

    public boolean complteSosoService(Long memberSq, Long orders_id, String ordersMerchantUid, String ordersImpUid) {

        Optional<SosoOrderDTO> sosoOrderDTOOptional = sosoOrderRepository.findById(orders_id);
        SosoOrderDTO sosoOrderDTO = sosoOrderDTOOptional.get();

        try {
            sosoOrderDTO.setOrderEnable(true);
            sosoOrderDTO.setOrdersSave(true);

            sosoOrderRepository.save(sosoOrderDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean deleteSosoService(Long memberSq, Long orders_id, String ordersMerchantUid, String ordersImpUid) {

        try {
            if (ordersMerchantUid != null && ordersImpUid != null) {

                CancelData cancelData = new CancelData(ordersImpUid, true);
                imIamportClient.cancelPaymentByImpUid(cancelData);

            }

            sosoOrderRepository.deleteById(orders_id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<SosoMenuDTO> getMenuLimitByDef(String menuLimit) {
        return sosoMenuRepository.findAllByMenuLimit(menuLimit);
    }

    public int getNomalTotalPrice(List<Long> menuSq, List<Integer> menuSize) {

        int totalResult = 0;

        for (int i = 0; i < menuSq.size(); i++) {

            Optional<SosoMenuDTO> sosoMenuDTO = sosoMenuRepository.findById(menuSq.get(i));

            totalResult += sosoMenuDTO.get().getMenuSosoPrice() * menuSize.get(i);

        }

        return totalResult;
    }
}


