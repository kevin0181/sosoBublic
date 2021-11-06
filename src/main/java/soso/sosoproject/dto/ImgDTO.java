package soso.sosoproject.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "img")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id") // 추가
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

    @Column
    private String img_date;

    @ManyToOne
    @JoinColumn(name = "menu_sq", insertable = false, updatable = false)
    private MenuDTO menuDTO;
}
