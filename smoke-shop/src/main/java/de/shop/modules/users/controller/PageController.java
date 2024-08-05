package de.shop.modules.users.controller;

import de.shop.modules.users.domain.entity.UserEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserProvider;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*
Это тестовый контроллер.
Тут показан вариант, как использовать данные пользователя через весь проект.
 */
@RestController
@RequestMapping("/user")
public class PageController {
    private UserProvider userProvider; // Провадер пользователя, который дает возможность вытянуть данные пользователя.
    public PageController(UserProvider provider) {
        this.userProvider = provider;
    }
    @GetMapping("/info")
    @Secured("ROLE_USER") // Разрешено только участнику

    public String test() {
        UserObject u = userProvider.getUserObject(); // получаем данные пользователя.

        return "Access: "+u.getEmail()+"/"+u.getId();
    }

}
