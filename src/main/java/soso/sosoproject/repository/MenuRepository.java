package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soso.sosoproject.dto.MenuDTO;

import java.util.List;

public interface MenuRepository extends JpaRepository<MenuDTO, Long> {
    List<MenuDTO> findAllByMenuName(String menuname);

    @Query(value = "SELECT menu_today from menu", nativeQuery = true)
    List<Long> findAllByMenuToday();
}
