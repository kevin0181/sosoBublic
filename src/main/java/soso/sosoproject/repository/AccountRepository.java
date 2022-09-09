package soso.sosoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import soso.sosoproject.dto.MemberDTO;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<MemberDTO, Long> {

    MemberDTO findByMemberEmail(String email);

    boolean existsByMemberEmail(String email);

    @Query(value = "SELECT count(*) from member", nativeQuery = true)
    int countTotalMember(); //전체 멤버 수 가져옴

    MemberDTO findByMemberPhonenumberAndMemberName(String phone, String name); //아아디 찾기

    MemberDTO findByMemberEmailAndMemberName(String email, String name); //비번 찾기

    MemberDTO findAllByMemberName(String name);

    MemberDTO findBySocialId(String id);

}
