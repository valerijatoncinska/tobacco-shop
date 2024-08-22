package de.shop.core.ctrl_panel.controller;

import de.shop.core.components.Validate;
import de.shop.core.ctrl_panel.domain.dto.InputRoleIdDto;
import de.shop.core.ctrl_panel.service.AdminUsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для кпользователей в контроль понели
 */
@RestController
@RequestMapping("/ctrl-panel/users")
@Secured("ROLE_ADMIN")
public class AdminUsersController {
    private AdminUsersService service;
private Validate validate;
    public AdminUsersController(AdminUsersService service,Validate validate) {
        this.service = service;
        this.validate = validate;
    }

    /**
     * Метод удаляет роль у пользователя
      * @param id    id пользователя
     * @param roleid id связующей записи из таблицы user_role
     * @return вернет OutputUserDto
     */
    @DeleteMapping("/{id}/role/{roleid}")
    public ResponseEntity<?> dropRole(@PathVariable Long id, @PathVariable Long roleid) {
        return ResponseEntity.ok(service.dropRole(id,roleid));
    }

    /**
     * Выводит список пользователей
     * @return List<>
     */
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.list());
    }

    /**
     * Метод отдает информацию о конкретном пользователе
     * @param id id пользователя
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> info(@PathVariable Long id) {
        return ResponseEntity.ok(service.info(id));
    }

    /**
     * метод добавляет роль пользователю
     * @param id id пользователя
     * @param dto входящией данные
     * @return вернет OutputUserDto
     */
    @PostMapping("/{id}/role")
    public ResponseEntity<?> addRole(@PathVariable Long id, @RequestBody InputRoleIdDto dto) {
    validate.notBlank(String.valueOf(dto.getRole()),"empty role");
        return ResponseEntity.ok(service.addRole(id,dto));
    }


}
