package de.shop.modules.users.controller;

import de.shop.core.components.LanguageResolver;
import de.shop.core.components.Validate;
import de.shop.modules.users.domain.dto.InputAddressDto;
import de.shop.modules.users.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Properties;

/**
 * Контроллер адресов пользователя
 */
@RestController
@RequestMapping("/address")
@Secured("ROLE_USER") // доступно тем, кто имеет роль пользователя
public class AddressController {
    private LanguageResolver lang; // язык
    private Validate validate; // валидация
    private AddressService service; // сервис

    public AddressController(LanguageResolver lang, Validate validate, AddressService service) {
        this.lang = lang;
        this.validate = validate;
        this.service = service;
    }

    /**
     * Удаление адреса
     *
     * @param id принимает id адреса
     * @return вернет 204 или 404
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> drop(@PathVariable Long id) {
        if (service.drop(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Просмотр адреса по id
     *
     * @param id адрес id
     * @return Возвращает OutputAddressDto
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> addressInfo(@PathVariable Long id) {
        return ResponseEntity.ok(service.info(id));
    }

    /**
     * Редактор адреса
     *
     * @param id  id адреса
     * @param dto Принимает входящий dto
     * @return выводит OutputAddressDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody InputAddressDto dto) {
        Properties p = lang.load("users", "address");
        validate.notBlank(dto.getStreet(), ((String) p.get("not.blank")).replace("[column]", "Street"));
        validate.notBlank(dto.getHouse(), ((String) p.get("not.blank")).replace("[column]", "house"));
        validate.notBlank(dto.getRegion(), ((String) p.get("not.blank")).replace("[column]", "Region"));
        validate.notBlank(dto.getLocality(), ((String) p.get("not.blank")).replace("[column]", "Locality"));
        validate.notBlank(dto.getPhone(), ((String) p.get("not.blank")).replace("[column]", "Phone"));
        validate.postalCode(dto.getPostalCode(), ((String) p.get("postal.code.error")));
        validate.email(dto.getEmail(), ((String) p.get("error.email")));
        validate.notBlank(dto.getName(), ((String) p.get("not.blank")).replace("[column]", "name"));


        return ResponseEntity.ok(service.update(id, dto));
    }

    /**
     * Выводит список адресов
     *
     * @return возвращает List<OutputAddressDto></OutputAddressDto>
     */
    @GetMapping
    public ResponseEntity<?> listAddress() {
        return ResponseEntity.ok(service.list());
    }

    /**
     * Добавляет адрес
     *
     * @param dto Входящий dto
     * @return возвращает OutputAddressDto
     */
    @PostMapping
    public ResponseEntity<?> newAddress(@RequestBody InputAddressDto dto) {
        Properties p = lang.load("users", "address");
        validate.notBlank(dto.getStreet(), ((String) p.get("not.blank")).replace("[column]", "Street"));
        validate.notBlank(dto.getHouse(), ((String) p.get("not.blank")).replace("[column]", "house"));
        validate.notBlank(dto.getRegion(), ((String) p.get("not.blank")).replace("[column]", "Region"));
        validate.notBlank(dto.getLocality(), ((String) p.get("not.blank")).replace("[column]", "Locality"));
        validate.notBlank(dto.getPhone(), ((String) p.get("not.blank")).replace("[column]", "Phone"));
        validate.postalCode(dto.getPostalCode(), ((String) p.get("postal.code.error")));
        validate.email(dto.getEmail(), ((String) p.get("error.email")));
        validate.notBlank(dto.getName(), ((String) p.get("not.blank")).replace("[column]", "name"));

        return ResponseEntity.ok(service.save(dto));
    }


}
