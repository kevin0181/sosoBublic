package soso.sosoproject.service.admin.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.repository.AccountRepository;

import java.util.List;

@Service
public class AdminMemberService {

    @Autowired
    private AccountRepository accountRepository;

    public List<MemberDTO> getMemberList() {
        return accountRepository.findAll();
    }

    public void deleteMember(List<Long> memberSq) {
        accountRepository.deleteAllById(memberSq);
    }
}
