package soso.sosoproject.dto.detail;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.RoleDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class UserDetail implements UserDetails {

    //userDetails인터페이스를 상속받아 로그인할때 사용함.

    private MemberDTO memberDTO;

    public UserDetail(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<RoleDTO> roles = memberDTO.getRole();
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        for (RoleDTO role : roles) {
            list.add(new SimpleGrantedAuthority(role.getRole_name()));
        }
        return list;
    }

    @Override
    public String getPassword() {
        return memberDTO.getMember_password();
    }

    @Override
    public String getUsername() {
        return memberDTO.getMember_email();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
