package soso.sosoproject.dto.kiosk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KioskMenuDTO {

    private Long menuSq;

    private String menuName;

    private String menuPrice;

    private boolean menuSoldOut;

    private boolean menuEnable;

    private List<KioskOrderSide> addSide = new ArrayList<>();

    private int size;
}
