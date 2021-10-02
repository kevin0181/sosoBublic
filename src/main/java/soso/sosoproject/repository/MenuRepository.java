package soso.sosoproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soso.sosoproject.dto.MenuDTO;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuDTO, Long> {
    List<MenuDTO> findAllByMenuName(String menuname); //해당메뉴 이름에 맞는 리스트 가져옴

    @Query(value = "SELECT menu_today from menu", nativeQuery = true)
    List<Long> findAllByMenuToday();//오늘의 메뉴 가져옴

    Page<MenuDTO> findAllByOrderByMenuSqDesc(Pageable pageable); //메뉴 리스트 반대로 가져옴

    List<MenuDTO> findByMenuNameContains(String searchText); //검색
}
