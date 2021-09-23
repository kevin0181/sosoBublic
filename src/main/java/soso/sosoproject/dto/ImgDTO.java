package soso.sosoproject.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "img")
public class ImgDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long img_sq;

    @Column(name = "menu_sq")
    private Long menuSq;

    @Column
    private String img_path;

    @Column
    private String img_name;

    @ManyToOne
    @JoinColumn(name = "menu_sq", insertable = false, updatable = false)
    private MenuDTO menuDTO;
}
