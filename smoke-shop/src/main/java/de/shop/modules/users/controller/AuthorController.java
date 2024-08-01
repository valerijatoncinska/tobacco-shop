package de.shop.modules.users.controller;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.ResponseDto;
import de.shop.core.components.Validate;
import de.shop.modules.users.domain.dto.InputLoginDto;
import de.shop.modules.users.domain.dto.InputRefreshTokenDto;
import de.shop.modules.users.domain.dto.InputRegDto;
import de.shop.modules.users.domain.dto.OutputRegDto;
import de.shop.modules.users.service.AuthorService;
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
    public ResponseEntity<ResponseDto<?>> refresh(@RequestBody InputRefreshTokenDto inputRefreshTokenDto) {
        // Будьте внимательны! Этот метод не работает, но он будет доделан
        Properties p = lang.load("users", "reg");
        validate.notBlank(inputRefreshTokenDto.getRefresh(), ((String) p.get("refresh.nodfound")));
        System.out.println("Refresh. Входим в контроллер \n");
        return ResponseEntity.ok(service.refresh(inputRefreshTokenDto));
    }

    /**
     * Метод принимает данные с клиента для аутентификации.
     *
     * @param inputLoginDto класс, содержащий данные от клиента. Конкретно email и пароль
     * @return возвращает ResponseEntity с вложенным ResponseDto
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<?>> login(@RequestBody InputLoginDto inputLoginDto) {
        Properties p = lang.load("users", "reg");
        validate.email(inputLoginDto.getEmail(), ((String) p.get("validate.email")).replace("[column]", "email"));
        validate.notBlank(inputLoginDto.getPassword(), ((String) p.get("empty.column")).replace("[column]", "password"));

        return ResponseEntity.ok(service.login(inputLoginDto));

    }

    /**
     * Метод, принимает данные с клиента для регистрации нового пользователя
     *
     * @param inputRegDto класс, содержит данные для регистрации. В конкретном случае email и password
     * @return возвращает ResponseEntity  с вложенным ResponseDto
     */
    @PostMapping("/reg")
    public ResponseEntity<ResponseDto<?>> reg(@RequestBody InputRegDto inputRegDto) {
        Properties p = lang.load("users", "reg");

        validate.email(inputRegDto.getEmail(), ((String) p.get("validate.email")).replace("[column]", "email"));
        validate.notBlank(inputRegDto.getPassword(), ((String) p.get("empty.column")).replace("[column]", "password"));
        validate.password(inputRegDto.getPassword(), 10, 64, ((String) p.get("password.validate")).replace("[column]", "password").replace("[min", "10").replace("[max]", "64"));


        return ResponseEntity.ok(service.reg(inputRegDto));
    }

//    /**
//     * Тестовый метод
//     *
//     * @return возвращает ResponseEntity.ok(ResponseDto<?>)
//     */
//    @GetMapping("/test")
//    public ResponseEntity<ResponseDto<?>> test() {
//        OutputRegDto user = new OutputRegDto();
//        user.setId(1L);
//        user.setEmail("support@sport-car.de");
//        return ResponseEntity.ok(new ResponseDto<>(true, user, "ok", lang.getCurrentLang()));
//    }

}
