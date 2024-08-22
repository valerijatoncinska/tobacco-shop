package de.shop.modules.users.jwt;

import de.shop.modules.users.domain.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserInfo implements UserDetails {
    private UserEntity userEntity;

    public UserInfo(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public String getEmail() {
        return getUserEntity().getEmail();
    }

    @Override
    public String getUsername() {
        return getUserEntity().getEmail();
    }

    @Override
    public String getPassword() {
        return getUserEntity().getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserEntity().getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return getUserEntity().isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return getUserEntity().isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getUserEntity().isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return getUserEntity().isEnabled();
    }
}
