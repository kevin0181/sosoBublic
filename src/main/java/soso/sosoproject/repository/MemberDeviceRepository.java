package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.MemberDeviceDTO;

public interface MemberDeviceRepository extends JpaRepository<MemberDeviceDTO, Long> {
}
