package soso.sosoproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soso.sosoproject.dto.PasMenuDTO;

import java.util.List;

public interface PasMenuRepository extends JpaRepository<PasMenuDTO, Long> {
    List<PasMenuDTO> findAllByMenuName(String menuname); //해당메뉴 이름에 맞는 리스트 가져옴

    @Query(value = "SELECT menu_today from menu", nativeQuery = true)
    List<Long> findAllByMenuToday();//오늘의 메뉴 가져옴

    Page<PasMenuDTO> findAllByOrderByMenuSqDesc(Pageable pageable); //메뉴 리스트 반대로 가져옴

    List<PasMenuDTO> findByMenuNameContains(String searchText); //메뉴 이름 검색

    List<PasMenuDTO> findAllByMenuEnable(boolean active); //active 검색

    List<PasMenuDTO> findAllByMenuCategorySq(Long categorySq);

    PasMenuDTO findByMenuToday(boolean todayId);   //오늘의 메뉴 하나가져옴

}
