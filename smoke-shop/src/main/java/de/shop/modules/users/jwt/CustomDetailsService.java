package de.shop.modules.users.jwt;

import de.shop.core.components.LanguageResolver;
import de.shop.core.exceptions.LoadUserByUsernameException;
import de.shop.modules.users.domain.entity.UserEntity;
import de.shop.modules.users.repository.interfaces.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс, реализующий интерфейс UserDetailsService
 */
@Component
public class CustomDetailsService implements UserDetailsService {
    private UserRepository repository;
    private LanguageResolver lang;
    private Properties p;

    public CustomDetailsService(LanguageResolver lang, UserRepository repository) {
        {
            this.p = lang.load("users", "reg");
            this.repository = repository;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws LoadUserByUsernameException {
        System.out.println("LoadUserByUsername. Загрузка пользователя с email: " + username);
        UserEntity user = repository.findByEmail(username)
                .orElseThrow(() -> new LoadUserByUsernameException("User not found"));
        System.out.println("loadUserByUsername. Пользователь найден: " + user.getEmail());
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );

    }
}

