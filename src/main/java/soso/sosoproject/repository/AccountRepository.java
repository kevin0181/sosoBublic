package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import soso.sosoproject.dto.MemberDTO;


@Repository
public interface AccountRepository extends JpaRepository<MemberDTO, Long> {

    MemberDTO findByMemberEmail(String email);

}
