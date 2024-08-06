package de.shop.modules.users.domain.entity;
// Внимание! Сущность используется для регистрации

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
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
    @Column(name = "is_adult", nullable = false)
    private boolean isadult;
    @Column(name = "subscribe_news", nullable = false)
    private boolean subscribeNews;
    @Column(name = "active_code")
    private String activeCode;
    @Column(name = "time_reg", nullable = false)
    private LocalDateTime timeReg;
    @Column(name = "time_visit", nullable = false)
    private LocalDateTime timeVisit;
    @Column(name = "active_code_expiry", nullable = false)
    private LocalDateTime activeCodeExpiry;
    @Column(name = "email_active")
    private boolean emailActive;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "userEntity")
    private Set<CartItemEntity> cartItemEntity;


    public UserEntity() {

    }

    public UserEntity(String email, String password, Set<RoleEntity> roles, boolean isadult, boolean subscribe_news, String activeCode, LocalDateTime timeReg, LocalDateTime timeVisit, LocalDateTime activeCodeExpiry) {
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.isadult = isadult;
        this.subscribeNews = subscribe_news;
        this.activeCode = activeCode;
        this.timeReg = timeReg;
        this.timeVisit = timeVisit;
        this.activeCodeExpiry = activeCodeExpiry;
        this.emailActive = false;
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


    public void setIsadult(boolean isadult) {
        this.isadult = isadult;
    }

    public void setSubscribeNews(boolean s) {
        this.subscribeNews = s;
    }

    public void setActiveCode(String code) {
        this.activeCode = code;
    }

    public void setTimeReg(LocalDateTime timeReg) {
        this.timeReg = timeReg;
    }

    public void setTimeVisit(LocalDateTime timeVisit) {
        this.timeVisit = timeVisit;
    }

    public void setActiveCodeExpiry(LocalDateTime expiry) {
        this.activeCodeExpiry = expiry;
    }

    public void setEmailActive(boolean a) {
        this.emailActive = a;
    }

    public void addRole(RoleEntity r) {
        this.roles.add(r);
    }


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

    public boolean getEmailActive() {
        return emailActive;
    }

    public String getEmail() {
        return email;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public LocalDateTime getTimeReg() {
        return timeReg;
    }

    public LocalDateTime getTimeVisit() {
        return timeVisit;
    }

    public LocalDateTime getActiveCodeExpiry() {
        return activeCodeExpiry;
    }

    public boolean getIsadult() {
        return isadult;
    }

    public boolean getSubscribeNews() {
        return subscribeNews;
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
