package de.shop.modules.users.service.interfaces;

import de.shop.modules.users.domain.dto.InputLoginDto;
import de.shop.modules.users.domain.dto.InputRegDto;
import de.shop.modules.users.domain.dto.OutputLoginDto;
import de.shop.modules.users.domain.dto.OutputRegDto;

/**
 * Интерфейс содержит обязательные методы для переопределения
 */
public interface Author {
    OutputRegDto reg(InputRegDto inputRegDto); // метод для регистрации

    OutputLoginDto login(InputLoginDto inputLoginDto); // метод для аутентификации
}
