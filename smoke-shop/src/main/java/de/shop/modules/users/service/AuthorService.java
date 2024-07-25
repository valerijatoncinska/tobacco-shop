package de.shop.modules.users.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.ResponseDto;
import de.shop.core.exceptions.*;
import de.shop.modules.users.domain.dto.*;
import de.shop.modules.users.domain.entity.RoleEntity;
import de.shop.modules.users.domain.entity.UserEntity;
import de.shop.modules.users.jwt.CustomDetailsService;
import de.shop.modules.users.jwt.JwtUtil;
import de.shop.modules.users.repository.interfaces.RoleRepository;
import de.shop.modules.users.repository.interfaces.UserRepository;
import de.shop.modules.users.service.interfaces.Author;
import de.shop.modules.users.service.mapping.AuthorMappingService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.security.SignatureException;
import java.util.Properties;

/**
 * Сервис для аутентификации, регистрации и обновления токенов
 */
@Service
public class AuthorService implements Author {
    private BCryptPasswordEncoder passwordEncoder; // класс хеширования пароля
    private LanguageResolver lang; // мультиязычность
    private UserRepository userRepository; // репозиторий пользователей
    private AuthenticationManager manager; // менеджер аутентификации
    private JwtUtil jwtUtil; // сервис токенов
    private Properties p; // Свойства языкового файла
    private AuthorMappingService map; // карта конвертации dto в entity
    private CustomDetailsService userDetailsService; // кастомный UserDetailsService
    private RoleRepository roleRepository; // репозиторий ролей

    public AuthorService(BCryptPasswordEncoder passwordEncoder, LanguageResolver lang, UserRepository userRepository, AuthenticationManager manager, JwtUtil jwtUtil, AuthorMappingService map, CustomDetailsService userDetailsService, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.lang = lang;
        this.userRepository = userRepository;
        this.manager = manager;
        this.jwtUtil = jwtUtil;
        this.p = lang.load("users", "author");
        this.map = map;
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    /**
     * Метод возвращает данные клиенту, после обновления токенов, отправляя их в контроллер
     *
     * @param inputRefreshTokenDto класс с входящим refresh token
     * @return возвращает ResponseDto с вложенными данными
     * @throws RefreshTokenException перехвадчик ошибок
     */
    public ResponseDto<?> refresh(InputRefreshTokenDto inputRefreshTokenDto) throws RefreshTokenException {
        p = lang.load("users", "reg");
        String refreshToken = inputRefreshTokenDto.getRefresh(); // входящий refresh token
        try {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Проверка, действителен ли refresh token
            if (jwtUtil.validateToken(refreshToken, userDetails)) {
                String accessToken = jwtUtil.generateAccessToken(userDetails);
                String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);
                OutputRefreshTokenDto outputRefreshTokenDto = new OutputRefreshTokenDto();
                outputRefreshTokenDto.setEmail(userDetails.getUsername());
                outputRefreshTokenDto.setAccessToken(accessToken);
                outputRefreshTokenDto.setRefreshToken(newRefreshToken);
                return new ResponseDto(true, outputRefreshTokenDto, "ok", lang.getCurrentLang());
            } else {
                throw new RefreshTokenException(((String) p.get("not.valid")));
            }
        } catch (ExpiredJwtException e){
            throw new RefreshTokenException(((String) p.get("not.valid")));
        } catch(MalformedJwtException | UnsupportedJwtException e){
            throw new RefreshTokenException(((String) p.get("not.valid")));
        }


    }

    /**
     * Метод, отдающий данные в контроллер после успешной аутентификации.
     *
     * @param inputLoginDto Класс, содержит входные данные
     * @return возвращает ResponseDto с вложенным OutputLoginDto
     * @throws LoadUserByUsernameException перехвадчик ошибок
     * @throws LoginException              перехвадчик ошибок
     */
    @Override
    public ResponseDto<?> login(InputLoginDto inputLoginDto) throws LoadUserByUsernameException, LoginException {
        p = lang.load("users","author");
        try {

            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            inputLoginDto.getEmail(),
                            inputLoginDto.getPassword()
                    ));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            OutputLoginDto outputLogin = new OutputLoginDto(userDetails.getUsername(), accessToken, refreshToken);
            return new ResponseDto<>(true, outputLogin, "ok", lang.getCurrentLang());
        } catch (BadCredentialsException e) {
            throw new LoginException(((String) p.get("not.found")));
        }
    }

    /**
     * Метод, производящий регистрацию и возвращающий ответ в контроллер
     *
     * @param ird класс, принимающий входные данные
     * @return возвращает ResponseDto с вложенным OutputRegDto
     * @throws RegException         перехвад ошибок
     * @throws RegConflictException перехвад ошибок, если такой пользователь уже есть
     * @throws DBException          перехват ошибок, связанный с базой
     */
    @Override
    public ResponseDto<?> reg(InputRegDto ird) throws RegException, RegConflictException, DBException {
// Далее нужно поискать пользователя в базе.
        if (userRepository.findByEmail(ird.getEmail()).isPresent()) {
            throw new RegConflictException(((String) p.get("user.conflict.reg")));
        }
        // поиск роли
        RoleEntity role = roleRepository.findByTitle("ROLE_USER").orElseThrow(() ->
                new DBException(((String) p.get("role.notfound")))
        );

// конвертация из dto в entity
        UserEntity newUser = map.mapDtoToEntity(ird);
        newUser.addRole(role); // добавить роль к новому пользователю
        newUser.setPassword(passwordEncoder.encode(ird.getPassword())); // хэшируем пароль
        try { // отправляем на регистрацию
            userRepository.save(newUser); // вносим в базу пользователя.
        } catch (DataAccessException e) {
            throw new DBException(e.getMessage());
        }
        // Конвиртируем entity в dto
        OutputRegDto newUserSuccess = map.mapEntityToDto(newUser);
        // возвращаем ответ.
        return new ResponseDto<>(true, newUserSuccess, "ok", lang.getCurrentLang());
    }


}