package de.shop.modules.users.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.exceptions.*;
import de.shop.core.services.EmailService;
import de.shop.core.services.RandomService;
import de.shop.core.services.ServerService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
    private EmailService mail;
    private ServerService server;
    private RandomService rand;

    public AuthorService(BCryptPasswordEncoder passwordEncoder, LanguageResolver lang, UserRepository userRepository, AuthenticationManager manager, JwtUtil jwtUtil, AuthorMappingService map, CustomDetailsService userDetailsService, RoleRepository roleRepository, EmailService mail, ServerService server, RandomService rand) {
        this.passwordEncoder = passwordEncoder;
        this.lang = lang;
        this.userRepository = userRepository;
        this.manager = manager;
        this.jwtUtil = jwtUtil;
        this.p = lang.load("users", "author");
        this.map = map;
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
        this.mail = mail;
        this.server = server;
        this.rand = rand;
    }

    /**
     * Метод возвращает данные клиенту, после обновления токенов, отправляя их в контроллер
     *
     * @param inputRefreshTokenDto класс с входящим refresh token
     * @return возвращает ResponseDto с вложенными данными
     * @throws RefreshTokenException перехвадчик ошибок
     */
    public OutputRefreshTokenDto refresh(InputRefreshTokenDto inputRefreshTokenDto) throws RefreshTokenException {
        p = lang.load("users", "token");
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
                return outputRefreshTokenDto;
            } else {
                throw new RefreshTokenException(((String) p.get("not.valid")));
            }
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenException(((String) p.get("not.valid")));
        } catch (MalformedJwtException | UnsupportedJwtException e) {
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
    public OutputLoginDto login(InputLoginDto inputLoginDto) throws LoadUserByUsernameException, LoginException {
        p = lang.load("users", "author");
        try {

            Authentication authentication = manager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            inputLoginDto.getEmail(),
                            inputLoginDto.getPassword()
                    ));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);
            OutputLoginDto outputLogin = new OutputLoginDto(userDetails.getUsername(), accessToken, refreshToken, userDetails.getAuthorities());
            return outputLogin;
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
    public OutputRegDto reg(InputRegDto ird) throws RegException, RegConflictException, DBException, UserSearchException {
        Properties p = lang.load("users", "reg");
// Далее нужно поискать пользователя в базе.
        if (userRepository.findByEmail(ird.getEmail()).isPresent()) {
            throw new RegConflictException(((String) p.get("user.conflict.reg")));
        }
        // поиск роли
        RoleEntity role = roleRepository.findByTitle("ROLE_USER").orElseThrow(() ->
                new UserSearchException(((String) p.get("role.notfound")))
        );

// конвертация из dto в entity
        UserEntity newUser = map.mapDtoToEntity(ird);
        newUser.addRole(role); // добавить роль к новому пользователю
        // Если пользователей в базе нет, тогда первым будет админ.
        if (userRepository.count() == 0) {
            RoleEntity roleAdmin = roleRepository.findByTitle("ROLE_ADMIN").orElseThrow(() ->
                    new UserSearchException(((String) p.get("role.notfound")))
            );
            newUser.addRole(roleAdmin); // добавили роль админа.
        }
        String uuid = rand.uuid(); // получаем uuid
        newUser.setPassword(passwordEncoder.encode(ird.getPassword())); // хэшируем пароль
        newUser.setTimeReg(LocalDateTime.now()); // дата регистрации
        newUser.setTimeVisit(LocalDateTime.now()); // последний визит
        newUser.setActiveCode(uuid); // передали uuid в entity
        newUser.setActiveCodeExpiry(LocalDateTime.now().plusDays(1)); // срок жизни активации
        newUser.setIsAdult(ird.getIsAdult());
        try { // отправляем на регистрацию
            userRepository.save(newUser); // вносим в базу пользователя.
        } catch (DataAccessException e) {
            throw new DBException(e.getMessage());
        }
        // Конвиртируем entity в dto
        OutputRegDto newUserSuccess = map.mapEntityToDto(newUser);
        // Тут будет отправка email письма.
        mail.setTemplate("reg-authen/reg-ok"); // Шаблон письма
        Map<String, String> vars = new HashMap<>();
        vars.put("link_active", server.getSite() + "/api/author/account-activate/" + uuid);
        mail.send(ird.getEmail(), ((String) p.get("account.mail.reg")), "", vars);


        // возвращаем ответ.
        return newUserSuccess;
    }


    /**
     * Метод, который подтверждает активацию аккаунта из ссылки электронного письма.
     *
     * @param uuid строка uuid
     * @return вернет строку
     * @throws UserSearchException отлов ошибок
     * @throws DBException
     */
    public String accountActivate(String uuid) throws UserSearchException, DBException {
        Properties p = lang.load("users", "activate");
        // ищем аккаунт по uuid, который был выдан при регистрации
        UserEntity r = userRepository.findByActiveCode(uuid).orElseThrow(() -> new UserSearchException("Аккаунт, для активации, не найден"));

// Далее нужно узнать, не истек ли срок активации.
        if (LocalDateTime.now().isAfter(r.getActiveCodeExpiry())) {
            return ((String) p.get("error.time"));
        }
        // изменяем некоторые данные
        r.setActiveCode(rand.uuid()); // Изменили uuid
        r.setActiveCodeExpiry(LocalDateTime.now()); // изменили время активации
        r.setEmailActive(true); // активировали аккаунт


// редактируем данные аккаунта
        try {
            userRepository.save(r);
            return ((String) p.get("activate.ok"));
        } catch (DataAccessException e) {
            throw new DBException(((String) p.get("error.db")));
        }
    }

    public Optional<UserEntity> findByEmailForProfile(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UserSearchException("User with that email is not found");
        }
        return userEntity;
    }


}