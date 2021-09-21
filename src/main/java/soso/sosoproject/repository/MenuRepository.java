package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.MenuDTO;

public interface MenuRepository extends JpaRepository<MenuDTO, Long> {
}
