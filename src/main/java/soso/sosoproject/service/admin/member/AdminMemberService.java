package soso.sosoproject.service.admin.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdminMemberService {

    @Autowired
    private AccountRepository accountRepository;

    public List<MemberDTO> getMemberList() {
        return accountRepository.findAll();
    }

    public int memberCount() {
        return accountRepository.countTotalMember();
    }

    public void deleteMember(List<Long> memberSq) {
//        List<MemberDTO> memberDTOList = accountRepository.findAllById(memberSq);
//        for (int i = 0; i < memberDTOList.size(); i++) {
//            accountRepository.delete(memberDTOList.get(i));
//        }
        accountRepository.deleteAllById(memberSq);
    }

    public MemberDTO getMemberDetail(Long memberSq) {
        Optional<MemberDTO> memberDTOOptional = accountRepository.findById(memberSq);
        return memberDTOOptional.get();
    }
}
