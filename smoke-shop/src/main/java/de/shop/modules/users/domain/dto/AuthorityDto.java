package de.shop.modules.users.domain.dto;

import org.springframework.security.core.GrantedAuthority;


public class AuthorityDto implements GrantedAuthority {

    private String authority;

    public AuthorityDto(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
