package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MenuImgDTO {
    private Long menu_sq;

    private String menu_name;

    private Long menuCategorySq;

    private String menu_contant;

    private int menu_price;

    private boolean menu_sold_out;

    private boolean menu_enable;

    private boolean menu_today;

    private List<MultipartFile> menu_img = new ArrayList<>();
}
