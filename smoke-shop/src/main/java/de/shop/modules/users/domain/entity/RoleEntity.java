package de.shop.modules.users.domain.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * Это сущность роли, проще говоря, прав пользователя
 */
@Entity
@Table(name = "role")
public class RoleEntity implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;

    public RoleEntity(String title) {
        this.title = title;
    }

    public RoleEntity() {

    }

    @Override
    public String getAuthority() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
