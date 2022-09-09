package soso.sosoproject.service.order;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soso.sosoproject.dto.*;
import soso.sosoproject.repository.MemberDeviceRepository;
import soso.sosoproject.repository.PasMenuRepository;
import soso.sosoproject.repository.PasOrderDetailRepository;
import soso.sosoproject.repository.PasOrderRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PasOrderService {


    @Autowired
    private PasOrderRepository pasOrderRepository;

    @Autowired
    private PasOrderDetailRepository pasOrderDetailRepository;

    @Autowired
    private PasMenuRepository pasMenuRepository;


    public void saveOrder(PasOrderDTO pasOrderDTO) {
        pasOrderRepository.save(pasOrderDTO);
    }

    public PasOrderDTO findOrderId(String ordersImpUid) {
        return pasOrderRepository.findByOrdersImpUid(ordersImpUid);
    }

    public List<PasOrderDTO> findOrderNotSave() {
        return pasOrderRepository.findAllByOrdersSave(false);
    }

    public List<PasOrderDTO> findAllPlaceOrder() {
        return pasOrderRepository.findAll();
    }

    public List<PasOrderDTO> findAllPlaceAndEnableOrder() {
        return pasOrderRepository.findAllByOrderEnableOrderByOrderDateDesc(false);
    }


    private IamportClient imIamportClient;

    public PasOrderService() {
        // REST API 키와 REST API secret 를 아래처럼 순서대로 입력한다.
        this.imIamportClient =
                new IamportClient("1152819197412694",
                        "acffbee8c37f2492f2654739c30af6863c53e981f2488325703fb8d691f222814862c2ab7d67779a");
    }


    public String saveFirstOrder(PasOrderDTO pasOrderDTO, int totalPay) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer(); //랜덤 난수 설정

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        sb.append(dtf.format(LocalDateTime.now()));
        int num = 0;
        do {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }

        } while (sb.length() < 20);
        String uid = sb.toString();
//        pasOrderDTO.setOrdersMerchantUid(uid); 미리 저장 X;
//        pasOrderDTO.setOrdersTotalPrice(Integer.toString(totalPay));
//        pasOrderRepository.save(pasOrderDTO);
        return uid;
    }

    public PasOrderDTO findUid(String ordersMerchantUid) {
        return pasOrderRepository.findAllByOrdersMerchantUid(ordersMerchantUid);
    }


    public void saveSosoOrder(PasOrderDTO pasOrderDTO) {

        pasOrderRepository.save(pasOrderDTO);

    }
//
//    @Transactional
//    public void cancleMenuService(String uid) {
//        pasOrderRepository.deleteByOrdersMerchantUid(uid);
//    }

    public boolean compltePasService(Long memberSq, Long orders_id, String ordersMerchantUid, String ordersImpUid) {
        Optional<PasOrderDTO> sosoOrderDTOOptional = pasOrderRepository.findById(orders_id);
        PasOrderDTO pasOrderDTO = sosoOrderDTOOptional.get();
        try {
            pasOrderDTO.setOrderEnable(true);
//            pasOrderDTO.setOrdersSave(true);

            pasOrderRepository.save(pasOrderDTO);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deletePasService(Long memberSq, Long orders_id, String ordersMerchantUid, String ordersImpUid) {
        try {
            if (ordersMerchantUid != null && ordersImpUid != null) {

                CancelData cancelData = new CancelData(ordersImpUid, true);
                imIamportClient.cancelPaymentByImpUid(cancelData);

            }

            pasOrderRepository.deleteById(orders_id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkTotalAmount(PasOrderDTO pasOrderDTO) {
        int resultTotal = 0;

        for (int i = 0; i < pasOrderDTO.getOrdersMenu().size(); i++) {
            Optional<PasMenuDTO> pasMenuDTOOptional = pasMenuRepository.findById(pasOrderDTO.getOrdersMenu().get(i).getMenuSq());

            resultTotal += pasMenuDTOOptional.get().getMenu_price() * pasOrderDTO.getOrdersMenu().get(i).getMenuOrderSize();

        }

        if (Integer.parseInt(pasOrderDTO.getOrdersTotalPrice()) == resultTotal) {
            return true;
        } else {
            return false;
        }
    }

    public List<PasOrderDTO> findAllOrderList() {
        return pasOrderRepository.findAll();
    }

    public List<PasOrderDTO> findMemberOrderList(Long memberSq) {
        return pasOrderRepository.findAllByMemberSq(memberSq);
    }

    public void saveTime(String ordersImpUid, String time) {
        PasOrderDTO pasOrderDTO = pasOrderRepository.findByOrdersImpUid(ordersImpUid);
        pasOrderDTO.setOrdersTime(time);
        pasOrderDTO.setOrdersSave(true);

        pasOrderRepository.save(pasOrderDTO);

    }

    public PasOrderDTO getOrderImpUid(String ordersImpUid) {
        PasOrderDTO pasOrderDTO = pasOrderRepository.findByOrdersImpUid(ordersImpUid);
        return pasOrderDTO;
    }

    public boolean deletePasOrderList(List<Long> pasCheck) {
        pasOrderRepository.deleteAllById(pasCheck);
        return true;
    }
}
