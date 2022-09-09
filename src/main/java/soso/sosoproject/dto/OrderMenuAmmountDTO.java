package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMenuAmmountDTO {
    private Long menuSq;
    private int menuSize;

    public OrderMenuAmmountDTO() {

    }

    public OrderMenuAmmountDTO(Long menuSq, int menuSize) {
        this.menuSq = menuSq;
        this.menuSize = menuSize;
    }
}
