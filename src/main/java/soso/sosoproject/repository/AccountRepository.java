package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soso.sosoproject.dto.MemberDTO;


@Repository
public interface AccountRepository extends JpaRepository<MemberDTO, Long> {

    MemberDTO findByMemberEmail(String email);

    boolean existsByMemberEmail(String email);

    @Query(value = "SELECT count(*) from member", nativeQuery = true)
    int countTotalMember();

    MemberDTO findByMemberPhonenumberAndMemberName(String phone, String name);

}
