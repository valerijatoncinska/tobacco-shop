package de.shop.modules.users.jwt;

import de.shop.modules.users.domain.entity.UserEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserProvider {
    private Collection<? extends GrantedAuthority> grant = null;
    public UserObject getUserObject() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a!=null && a.getPrincipal() instanceof UserInfo) {
UserInfo userInfo = (UserInfo) a.getPrincipal();
UserEntity u = userInfo.getUserEntity();
grant = a.getAuthorities();
return new UserObject(u.getId(),u.getEmail());
        }
        else {
            return null;
        }
    }
public boolean role(String role) {
        if (grant!=null) {
            for (GrantedAuthority a : grant) {
                if (a.getAuthority().equals(role)) {
                    return true;
                }
            }
        }
        return false;
}


}
