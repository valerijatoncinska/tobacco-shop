package de.shop.modules.users.jwt;

import de.shop.modules.users.domain.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserProvider {
    private Collection<? extends GrantedAuthority> grant = null;
    private UserEntity userEntity;

    public UserObject getUserObject() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a != null && a.getPrincipal() instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) a.getPrincipal();
            userEntity = userInfo.getUserEntity();
            grant = a.getAuthorities();
            return new UserObject(userEntity.getId(), userEntity.getEmail());
        } else {
            return null;
        }
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public boolean role(String role) {
        if (grant != null) {
            for (GrantedAuthority a : grant) {
                if (a.getAuthority().equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }


}
