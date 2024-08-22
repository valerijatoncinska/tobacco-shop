package de.shop.modules.users.jwt;

import de.shop.core.components.LanguageResolver;
import de.shop.core.exceptions.AccessException;
import de.shop.core.exceptions.LoadUserByUsernameException;
import de.shop.modules.users.domain.entity.UserEntity;
import de.shop.modules.users.repository.interfaces.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Properties;

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
            this.repository = repository;
            this.lang = lang;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws LoadUserByUsernameException, AccessException {
        p = lang.load("users", "author");
        UserEntity user = repository.findByEmail(username)
                .orElseThrow(() -> new LoadUserByUsernameException(((String) p.get("user.notfound"))));
        if (!user.getEmailActive()) {
            throw new AccessException(((String) p.get("account.no.activate")));
        }
        user.setTimeVisit(LocalDateTime.now());
        try {
            repository.save(user);
        } catch (DataAccessException e) {
        }
        return new UserInfo(user);
    }
}

