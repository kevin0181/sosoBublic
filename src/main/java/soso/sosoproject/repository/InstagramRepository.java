package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soso.sosoproject.dto.InstagramTagDTO;

public interface InstagramRepository extends JpaRepository<InstagramTagDTO, Long> {
}
