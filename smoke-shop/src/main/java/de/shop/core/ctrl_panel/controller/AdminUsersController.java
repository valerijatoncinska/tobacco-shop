package de.shop.core.ctrl_panel.controller;

import de.shop.core.components.Validate;
import de.shop.core.ctrl_panel.domain.dto.InputRoleIdDto;
import de.shop.core.ctrl_panel.service.AdminUsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}/role/{roleid}")
    public ResponseEntity<?> dropRole(@PathVariable Long id, @PathVariable Long roleid) {
        return ResponseEntity.ok(service.dropRole(id,roleid));
    }

    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> info(@PathVariable Long id) {
        return ResponseEntity.ok(service.info(id));
    }
    @PostMapping("/{id}/role")
    public ResponseEntity<?> addRole(@PathVariable Long id, @RequestBody InputRoleIdDto dto) {
    validate.notBlank(String.valueOf(dto.getRole()),"empty role");
        return ResponseEntity.ok(service.addRole(id,dto));
    }


}
