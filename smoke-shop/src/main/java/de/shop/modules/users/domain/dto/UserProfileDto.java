package de.shop.modules.users.domain.dto;
import de.shop.modules.users.domain.entity.RoleEntity;

import java.util.HashSet;
import java.util.Set;


public class UserProfileDto {

    private Long id;

    private String email;

    private String password;

    private Set<RoleEntity> roles = new HashSet<>();

    private Set<AuthorityDto> authorities = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void setAuthorities(Set<AuthorityDto> authorities) {
        this.authorities = authorities;
    }


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

}
