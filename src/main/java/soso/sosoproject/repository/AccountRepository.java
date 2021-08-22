package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soso.sosoproject.dto.SignupDTO;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<SignupDTO, Long> {
    Optional<SignupDTO> findByMember_email(String member_email);
}
