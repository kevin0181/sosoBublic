package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.RoleDTO;

public interface RoleRepository extends JpaRepository<RoleDTO, Long> {

    RoleDTO findByRoleName(String id);

}
