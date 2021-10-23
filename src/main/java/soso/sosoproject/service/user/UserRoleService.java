package soso.sosoproject.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.repository.AccountRepository;

import java.util.Optional;

@Service
public class UserRoleService {
    @Autowired
    private AccountRepository accountRepository;

    public Optional<MemberDTO> getRole(Long memberSq) {
        return accountRepository.findById(memberSq);
    }
}
