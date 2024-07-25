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

    public CustomDetailsService(LanguageResolver lang,UserRepository repository) {
        {
      this.repository = repository;
      this.lang = lang;
        }
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws LoadUserByUsernameException {
        p = lang.load("users", "author");

        UserEntity user = repository.findByEmail(username)
                .orElseThrow(() -> new LoadUserByUsernameException(((String) p.get("user.notfound"))));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
                );

    }
}

