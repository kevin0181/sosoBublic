package soso.sosoproject.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soso.sosoproject.dto.PasOrderDTO;
import soso.sosoproject.repository.PasOrderDetailRepository;
import soso.sosoproject.repository.PasOrderRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class PasOrderService {

    @Autowired
    private PasOrderRepository pasOrderRepository;

    @Autowired
    private PasOrderDetailRepository pasOrderDetailRepository;

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
        pasOrderDTO.setOrdersMerchantUid(uid);
        pasOrderDTO.setOrdersTotalPrice(Integer.toString(totalPay));
        pasOrderRepository.save(pasOrderDTO);
        return uid;
    }

    public PasOrderDTO findUid(String ordersMerchantUid) {
        return pasOrderRepository.findAllByOrdersMerchantUid(ordersMerchantUid);
    }


    public void saveSosoOrder(PasOrderDTO pasOrderDTO) {

        pasOrderRepository.save(pasOrderDTO);

    }

    @Transactional
    public void cancleMenuService(String uid) {
        pasOrderRepository.deleteByOrdersMerchantUid(uid);
    }
}
