package de.shop.core.ctrl_panel.controller;

import de.shop.core.ctrl_panel.service.AdminUsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ctrl-panel/users")
@Secured("ROLE_ADMIN")
public class AdminUsersController {
    private AdminUsersService service;
    public AdminUsersController(AdminUsersService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.list());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> info(@PathVariable Long id) {
        return ResponseEntity.ok(service.info(id));
    }
}
