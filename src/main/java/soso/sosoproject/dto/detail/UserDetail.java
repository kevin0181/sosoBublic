package soso.sosoproject.dto.detail;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import soso.sosoproject.dto.MemberDTO;
import soso.sosoproject.dto.RoleDTO;

import java.util.*;

public class UserDetail implements UserDetails, OAuth2User {

    //userDetails인터페이스를 상속받아 로그인할때 사용함.
    private MemberDTO memberDTO;

    private Map<String,Object> attributes;

    public UserDetail(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    public UserDetail(MemberDTO memberDTO,Map<String,Object> attributes) {
        this.memberDTO = memberDTO;
        this.attributes = attributes;
    }


    //oauth2
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }


    //로그인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<RoleDTO> roles = memberDTO.getRole();
        List<GrantedAuthority> list = new ArrayList<>();
        for (RoleDTO role : roles) {
            list.add(new SimpleGrantedAuthority(role.getRole_name()));
        }
        return list;
    }

    @Override
    public String getPassword() {
        return memberDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return memberDTO.getMemberEmail();
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

    public MemberDTO getMemberDTO() {
        return memberDTO;
    }

}
