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
        memberDTO.setPolicy(oauth2DTO.isPolicy());

        //권한 가져옴
        List<RoleDTO> role_id = roleRepository.findAll();
        RoleDTO role_id_l = role_id.get(1);

        //권한 부여
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole_sq(role_id_l.getRole_sq());

        memberDTO.getRole().add(roleDTO);

        accountRepository.save(memberDTO);

    }
}
