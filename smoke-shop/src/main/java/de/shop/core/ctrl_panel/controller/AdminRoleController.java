package de.shop.core.ctrl_panel.controller;

import de.shop.core.components.Validate;
import de.shop.core.ctrl_panel.domain.dto.InputRoleDto;
import de.shop.core.ctrl_panel.service.AdminRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * контроллер контроль панели для ролей
 */
@RestController
@RequestMapping("/ctrl-panel/roles")
public class AdminRoleController {
    private AdminRoleService service;
    private Validate validate;

    public AdminRoleController(AdminRoleService service, Validate validate) {
        this.service = service;
        this.validate = validate;
    }

    /**
     * Список ролей
     *
     * @return вернет List
     */
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.list());
    }

    /**
     * Добавляет роль в базу
     *
     * @param dto входящий запрос
     * @return вернет OutputRoleDto
     */
    @PostMapping
    public ResponseEntity<?> created(@RequestBody InputRoleDto dto) {
        validate.notBlank(dto.getTitle(), "empty title");
        return ResponseEntity.ok(service.created(dto));
    }

    /**
     * Редактирование роли
     *
     * @param id  id роли
     * @param dto входящие данные
     * @return вернет OutputRoleDto
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody InputRoleDto dto) {
        validate.notBlank(dto.getTitle(), "empty title"); // проверка на пустоту
        return ResponseEntity.ok(service.update(id, dto));
    }

    /**
     * Удаляет роль из базы
     *
     * @param id id роли в базе
     * @return возвращает статус
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }


}
