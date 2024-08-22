package de.shop.core.ctrl_panel.domain.entity;

import jakarta.persistence.*;

/**
 * Сущность из таблицы user_role, которая связывает пользователя и роль
 * Используется только в админ панели
 */
@Entity
@Table(name = "user_role")
public class AdminRoleItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "role_id")
    private Long roleId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setRoleId(Long id) {
        this.roleId = id;
    }

    public Long getRoleId() {
        return roleId;
    }

}
