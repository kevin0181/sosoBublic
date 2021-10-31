package soso.sosoproject.controller.user;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import soso.sosoproject.dto.OrderDTO;
import soso.sosoproject.service.admin.menu.MenuService;
import soso.sosoproject.service.order.OrderService;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@Controller
public class UserOrderController {

    private IamportClient imIamportClient;

    public UserOrderController() {
        // REST API 키와 REST API secret 를 아래처럼 순서대로 입력한다.
        this.imIamportClient =
                new IamportClient("1152819197412694",
                        "acffbee8c37f2492f2654739c30af6863c53e981f2488325703fb8d691f222814862c2ab7d67779a");
    }

    @Autowired
    private OrderService orderService;
    @Autowired
    private MenuService menuService;

    @PostMapping("/user/order/menu")
    @ResponseBody
    public boolean orderMenu(OrderDTO orderDTO) {
        try {
            orderService.saveOrder(orderDTO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping("/user/orderMenu/pay/ammount")
    @ResponseBody
    public int MenuAmmount(OrderDTO orderDTO) {
        int result = menuService.getOrderMenuAmmount(orderDTO.getOrdersMenu());
        return result;
    }

    public IamportResponse<Payment> paymentByImpUid(String imp_uid) throws IamportResponseException, IOException {
        return imIamportClient.paymentByImpUid(imp_uid);
    }
}
