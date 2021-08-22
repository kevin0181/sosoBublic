package soso.sosoproject.service.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.SignupDTO;
import soso.sosoproject.repository.AccountRepository;


@Service
public class MemberSignupService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(SignupDTO signupDTO) {
        //user 권한으로 회원가입
        String encodedPassword = passwordEncoder.encode(signupDTO.getMember_password());
        signupDTO.setMember_password(encodedPassword);

        signupDTO.setMember_role_sq(2l);
        accountRepository.save(signupDTO);
    }
}
