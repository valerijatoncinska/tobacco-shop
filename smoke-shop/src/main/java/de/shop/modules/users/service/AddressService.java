package de.shop.modules.users.service;

import de.shop.core.components.LanguageResolver;
import de.shop.core.exceptions.AddressNotfoundException;
import de.shop.core.exceptions.DBException;
import de.shop.modules.users.domain.dto.InputAddressDto;
import de.shop.modules.users.domain.dto.OutputAddressDto;
import de.shop.modules.users.domain.entity.AddressEntity;
import de.shop.modules.users.jwt.UserObject;
import de.shop.modules.users.jwt.UserProvider;
import de.shop.modules.users.repository.interfaces.AddressRepository;
import de.shop.modules.users.service.mapping.AddressMappingService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис адресов пользователя
 */
@Service
public class AddressService {
    private LanguageResolver lang; // язык
    private AddressRepository repository; // репозиторий
    private AddressMappingService mapping; // маппинг
    private UserProvider userProvider; // пользователь в системе

    /**
     * Конструктор
     *
     * @param repository   Репозиторий
     * @param lang         язык
     * @param mapping      маппинг
     * @param userProvider пользователь в системе
     */
    public AddressService(AddressRepository repository, LanguageResolver lang, AddressMappingService mapping, UserProvider userProvider) {
        this.repository = repository;
        this.lang = lang;
        this.mapping = mapping;
        this.userProvider = userProvider;
    }

    /**
     * Удаление адреса
     *
     * @param id адрес id
     * @return возвращает boolean
     */
    @Transactional
    public boolean drop(Long id) {
        UserObject u = userProvider.getUserObject(); // краткие данные пользователя в сети
        Optional<AddressEntity> a = repository.findByUserEntityIdAndId(u.getId(), id); // поиск адрес
        if (!a.isPresent()) { // если его нет, тогда false
            return false;
        }
        repository.deleteById(id); // удаляем адрес
        return true;
    }

    /**
     * Информация о конкретном адресе
     *
     * @param id адрес id
     * @return возвращает OutputAddressDto
     * @throws AddressNotfoundException перехват ошибок
     */
    public OutputAddressDto info(Long id) throws AddressNotfoundException {
        UserObject user = userProvider.getUserObject(); // Краткая информация о пользователе в системе

        Optional<AddressEntity> a = repository.findByUserEntityIdAndId(user.getId(), id); // поиск адрес
        if (!a.isPresent()) { // Если адрес не найден, кидаем ошибку
            throw new AddressNotfoundException("error");
        }
        AddressEntity address = a.get(); // Вытянули AddressEntity
        return mapping.mapEntityToDto(address); // выполнили конвертацию entity в dto и отдали контроллеру
    }

    /**
     * Метод обновления адреса
     *
     * @param id  адрес id
     * @param dto входящий dto
     * @return возвращает OutputAddressDto
     * @throws DBException              перехват ошибок
     * @throws AddressNotfoundException перехват ошибок
     */
    public OutputAddressDto update(Long id, InputAddressDto dto) throws DBException, AddressNotfoundException {
        UserObject user = userProvider.getUserObject(); // краткая информация о пользователе в сети

        Optional<AddressEntity> a = repository.findByUserEntityIdAndId(user.getId(), id); // поиск адрес
        if (!a.isPresent()) { // если не найден, кидаем ошибку
            throw new AddressNotfoundException("error");
        }

        AddressEntity entity = mapping.mapDtoToEntity(dto); // Конвертация из dto в entity
        entity.setId(id); // установили id
        entity.setUserEntity(userProvider.getUserEntity()); // установили UserEntity
        try {
            repository.save(entity); // сохранили
        } catch (DataAccessException e) {
            throw new DBException("error");
        }
        OutputAddressDto out = mapping.mapEntityToDto(entity); // конвертация и возврат

        return out; // вывод
    }

    /**
     * Список адресов
     *
     * @return возвращает List<OutputAddressDto></OutputAddressDto>
     */
    public List<OutputAddressDto> list() {
        UserObject u = userProvider.getUserObject(); // краткая информация о пользователе в системе
        return repository.findByUserEntityId(u.getId()).stream()
                .map(addressEntity -> mapping.mapEntityToDto(addressEntity))
                .toList();
    }

    /**
     * Метод, который добавляет адрес
     *
     * @param dto входящий dto
     * @return вернет OutputAddressDto
     * @throws DBException Перехват ошибок
     */
    public OutputAddressDto save(InputAddressDto dto) throws DBException {
        UserObject user = userProvider.getUserObject(); // краткая информация о пользователе в системе
        AddressEntity entity = mapping.mapDtoToEntity(dto); // конвертация из dto в entity;
        entity.setUserEntity(userProvider.getUserEntity()); // вставили UserEntity
        try {
            repository.save(entity); // сохранили в базу
        } catch (DataAccessException e) {
            throw new DBException("error");
        }
        OutputAddressDto out = mapping.mapEntityToDto(entity); // конвертируем entity в dto

        return out; // вывод
    }
}
