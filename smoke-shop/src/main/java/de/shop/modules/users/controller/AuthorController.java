package de.shop.modules.users.controller;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.Validate;
import de.shop.modules.users.domain.dto.InputLoginDto;
import de.shop.modules.users.domain.dto.InputRefreshTokenDto;
import de.shop.modules.users.domain.dto.InputRegDto;
import de.shop.modules.users.domain.dto.OutputLoginDto;
import de.shop.modules.users.service.AuthorService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;


@RestController
@RequestMapping("/author")
/**
 * Контроллер аутентификации, регистрации и обновления токенов
 */
public class AuthorController {
    private LanguageResolver lang; // для работы с мультиязычностью
    private Validate validate; // кастомный валидатор
    private AuthorService service; // сервис аутентификации, регистрации и обновления токенов

    public AuthorController(LanguageResolver lang, Validate validate, AuthorService service) {
        this.lang = lang;
        this.validate = validate;
        this.service = service;
    }

    /**
     * Метод, принимает refresh token и возвращает обновленную информацию
     *
     * @param inputRefreshTokenDto класс, в котором будет храниться refresh token, отправленный с клиента
     * @return возвращает ResponseEntity с вложенным ResponseDto
     */
    @PostMapping("refresh")
    public ResponseEntity<?> refresh(@RequestBody InputRefreshTokenDto inputRefreshTokenDto) {
        // Будьте внимательны! Этот метод не работает, но он будет доделан
        Properties p = lang.load("users", "reg");
        validate.notBlank(inputRefreshTokenDto.getRefresh(), ((String) p.get("refresh.nodfound")));
        return ResponseEntity.ok(service.refresh(inputRefreshTokenDto));
    }

    /**
     * Метод принимает данные с клиента для аутентификации.
     *
     * @param inputLoginDto класс, содержащий данные от клиента. Конкретно email и пароль
     * @return возвращает ResponseEntity с вложенным ResponseDto
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody InputLoginDto inputLoginDto, HttpServletResponse response) {
        Properties p = lang.load("users", "reg");
        validate.email(inputLoginDto.getEmail(), ((String) p.get("validate.email")).replace("[column]", "email"));
        validate.notBlank(inputLoginDto.getPassword(), ((String) p.get("empty.column")).replace("[column]", "password"));

        OutputLoginDto tokenDto = service.login(inputLoginDto);

        Cookie cookieAccess = new Cookie("ACCESS_TOKEN", tokenDto.getAccessToken());
        cookieAccess.setPath("/");
        cookieAccess.setHttpOnly(true);
        response.addCookie(cookieAccess);
        Cookie cookieRefresh = new Cookie("REFRESH_TOKEN", tokenDto.getRefreshToken());
        cookieRefresh.setPath("/");
        cookieRefresh.setHttpOnly(true);
        response.addCookie(cookieRefresh);

        return ResponseEntity.ok(service.login(inputLoginDto));

    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) {
        removeCookie(response);
    }

    private void removeCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("ACCESS_TOKEN", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * Метод, принимает данные с клиента для регистрации нового пользователя
     *
     * @param inputRegDto класс, содержит данные для регистрации. В конкретном случае email и password
     * @return возвращает ResponseEntity  с вложенным ResponseDto
     */
    @PostMapping("/reg")
    public ResponseEntity<?> reg(@RequestBody InputRegDto inputRegDto) {
        Properties p = lang.load("users", "reg");

        validate.email(inputRegDto.getEmail(), ((String) p.get("validate.email")).replace("[column]", "email"));
        validate.notBlank(inputRegDto.getPassword(), ((String) p.get("empty.column")).replace("[column]", "password"));
        validate.password(inputRegDto.getPassword(), 10, 31, ((String) p.get("password.validate")).replace("[column]", "password").replace("[min", "10").replace("[max]", "64"));
        validate.checked(inputRegDto.getIsAdult(), ((String) p.get("is_adult")));
        return ResponseEntity.ok(service.reg(inputRegDto));
    }

    /**
     * Метод, который принимает данные на активацию аккаунта и возвращает обратно результат
     *
     * @param uuid часть url, которая содержит уникальный ключ для активации
     * @return возвращает строковое значение
     */
    @GetMapping("/account-activate/{uuid}")
    public String accountActivate(@PathVariable String uuid) {
        return service.accountActivate(uuid);
    }


}
