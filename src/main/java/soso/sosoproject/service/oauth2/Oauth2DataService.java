package soso.sosoproject.service.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.Oauth2DTO;
import soso.sosoproject.dto.RoleDTO;
import soso.sosoproject.repository.AccountRepository;
import soso.sosoproject.repository.RoleRepository;
import soso.sosoproject.service.Account.EmailSendService;

import java.util.List;

@Service
public class Oauth2DataService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public MemberDTO findOauth2Member(String email) {
        return accountRepository.findByMemberEmail(email);
    }

    public void saveOauth2(Oauth2DTO oauth2DTO) {
        String randomPassword = new EmailSendService().certified_key();

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail(oauth2DTO.getMemberEmail());
        memberDTO.setMemberName(oauth2DTO.getMemberName());
        memberDTO.setMemberPhonenumber(oauth2DTO.getMemberPhonenumber());
        memberDTO.setMemberAddress(oauth2DTO.getMemberAddress());
        memberDTO.setPassword(passwordEncoder.encode(randomPassword));
        memberDTO.setCertiNumber(randomPassword);
        memberDTO.setSocialId(oauth2DTO.getSocialId());
        memberDTO.setPolicy(oauth2DTO.isPolicy());

        //권한 가져옴
        RoleDTO roleDTO = roleRepository.findByRoleName("ROLE_USER");

        //권한 부여
//        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole_sq(roleDTO.getRole_sq());

        memberDTO.getRole().add(roleDTO);

        accountRepository.save(memberDTO);

    }
}
