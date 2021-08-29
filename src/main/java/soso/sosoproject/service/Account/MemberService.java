package soso.sosoproject.service.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import soso.sosoproject.dto.RoleDTO;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.detail.UserDetail;
import soso.sosoproject.repository.AccountRepository;
import soso.sosoproject.repository.RoleRepository;

import java.util.List;
import java.util.Optional;


@Service
public class MemberService implements UserDetailsService {
//    implements UserDetailsService

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //회원가입
    public void save(MemberDTO memberDTO) {
        //user 권한으로 회원가입
        String encodedPassword = passwordEncoder.encode((memberDTO.getPassword()));
        memberDTO.setPassword(encodedPassword);

        //권한 가져옴
        List<RoleDTO> role_id = roleRepository.findAll();
        RoleDTO role_id_l = role_id.get(1);

        //권한 부여
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole_sq(role_id_l.getRole_sq());

        memberDTO.getRole().add(roleDTO);

        accountRepository.save(memberDTO);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        MemberDTO memberDTO = accountRepository.findByMemberEmail(username);

        if (memberDTO == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserDetail(memberDTO);
    }


    public boolean emailCheck(String email) {

        return accountRepository.existsByMemberEmail(email);
    }

}
