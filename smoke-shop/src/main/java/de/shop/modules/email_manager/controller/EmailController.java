package de.shop.modules.email_manager.controller;

import de.shop.core.components.LanguageResolver;
import de.shop.core.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/email")
public class EmailController {
    private LanguageResolver lang;
    private EmailService mail;
    public EmailController(LanguageResolver lang, EmailService mail) {
        this.lang = lang;
        this.mail = mail;
    }
    /*
    @GetMapping("/send")
    public ResponseEntity<ResponseDto<?>> send() {
        Properties p = lang.load("email_manager","words");
        mail.setTemplate("reg-authen/hello");
        Map<String, String> vars = new HashMap<>();
        vars.put("domain","http://localhost:8080");
        mail.send("artem95.dainov@gmail.com",((String) p.get("title")),((String) p.get("message")),vars);
        return ResponseEntity.ok(new ResponseDto(true,((String) p.get("send.ok")),"ok",lang.getCurrentLang()));
    }

 */
}
