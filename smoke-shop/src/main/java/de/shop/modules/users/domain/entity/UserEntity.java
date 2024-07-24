package de.shop.modules.users.domain.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сущность пользователя.
 * Внимание! приминяется для регистрации, аутентификации.
 * В будущем нужно будет переименовать его, к примеру RegEntity.
 */
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public UserEntity() {

    }

    public UserEntity(String email, String password, Set<RoleEntity> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    public void addRole(RoleEntity r) {
        this.roles.add(r);
    }
    // Геттеры и сеттеры для id, username, password, roles


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Ваша логика для проверки срока действия учетной записи
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Ваша логика для проверки заблокированности учетной записи
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Ваша логика для проверки срока действия учетных данных
    }

    @Override
    public boolean isEnabled() {
        return true; // Ваша логика для проверки активности учетной записи
    }
}
