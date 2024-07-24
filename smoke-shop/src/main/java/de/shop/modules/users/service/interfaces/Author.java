package de.shop.modules.users.service.interfaces;

import de.shop.core.components.ResponseDto;
import de.shop.modules.users.domain.dto.InputLoginDto;
import de.shop.modules.users.domain.dto.InputRegDto;
/**
 * Интерфейс содержит обязательные методы для переопределения
 */
public interface Author {
    ResponseDto<?> reg(InputRegDto inputRegDto); // метод для регистрации

    ResponseDto<?> login(InputLoginDto inputLoginDto); // метод для аутентификации
}
