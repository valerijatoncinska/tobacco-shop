package de.shop.modules.users.jwt;

import de.shop.modules.users.domain.entity.UserEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserProvider {
    public UserObject getUserObject() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a!=null && a.getPrincipal() instanceof UserInfo) {
UserInfo userInfo = (UserInfo) a.getPrincipal();
UserEntity u = userInfo.getUserEntity();
return new UserObject(u.getId(),u.getEmail());
        }
        else {
            return null;
        }
    }

}
