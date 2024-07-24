package de.shop.modules.users.controller;

import de.shop.core.components.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Класс для тестирования закрытых участков
 * Он просто тестовый и на него не нужно обращать внимание
 */
@RestController
@RequestMapping("/user")
public class PageController {
    @GetMapping("/page")
    public ResponseEntity<ResponseDto<?>> page() {
return ResponseEntity.ok(new ResponseDto<>(true,"Ты видешь закрытую информацию","ok","ru"));
    }
}
