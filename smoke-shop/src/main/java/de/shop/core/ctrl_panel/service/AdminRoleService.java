package de.shop.core.ctrl_panel.service;

import de.shop.core.ctrl_panel.domain.dto.InputRoleDto;
import de.shop.core.ctrl_panel.domain.dto.OutputRoleDto;
import de.shop.core.ctrl_panel.domain.entity.AdminRoleItemEntity;
import de.shop.core.ctrl_panel.repository.AdminRoleItemRepository;
import de.shop.core.ctrl_panel.repository.AdminRoleRepository;
import de.shop.core.exceptions.DBException;
import de.shop.core.exceptions.RegConflictException;
import de.shop.core.exceptions.UserSearchException;
import de.shop.modules.users.domain.entity.RoleEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис ролей
 */
@Service
public class AdminRoleService {
    private AdminRoleRepository repository;
    private AdminRoleItemRepository roleItemRepository;

    public AdminRoleService(AdminRoleRepository repository, AdminRoleItemRepository roleItemRepository) {
        this.repository = repository;
        this.roleItemRepository = roleItemRepository;
    }

    /**
     * Метод выводит все роли, которые есть в базе
     *
     * @return возвращает List<OutputRoleDto>
     */
    public List<OutputRoleDto> list() {
        // пропускаем через стрим
        return repository.findAll().stream()
                .map(entity -> {
                    // Заполняем данные для клиента
                    OutputRoleDto out = new OutputRoleDto();
                    out.setId(entity.getId());
                    out.setTitle(entity.getTitle());
                    return out;
                }).toList();
    }

    /**
     * Метод для обновления роли
     *
     * @param id  id роли
     * @param dto входящий dto
     * @return вернет OutputRoleDto
     * @throws RegConflictException отлов ошибок
     * @throws DBException          отлов ошибок
     * @throws UserSearchException  отлов ошибок
     */
    public OutputRoleDto update(Long id, InputRoleDto dto) throws RegConflictException, DBException, UserSearchException {
        // поиск роли
        Optional<RoleEntity> opt = repository.findById(id);
        if (!opt.isPresent()) { // если роли нет
            throw new UserSearchException("o.o");
        }
        RoleEntity entity = opt.get(); // получили сущность
// поиск роли по имени, которое приходит из входящего dto
        Optional<RoleEntity> optTitle = repository.findByTitle(dto.getTitle());
        if (optTitle.isPresent()) { // роль по такому имени найдена
            RoleEntity entityTitle = optTitle.get(); // получили сущность
            if (entityTitle.getId() != entity.getId()) { // если редактируемая и найденная роль по имени имеют разный id, тогда кидаем ошибку. Если id одинаковый, просто сохраняем
                throw new RegConflictException("error");
            }
        }

        entity.setTitle(dto.getTitle()); // изменили имя роли
        try { // сохраняем сущность
            repository.save(entity);
        } catch (DataAccessException e) {
            System.out.println("500 - internal server error \n " + e.getMessage());
            throw new DBException("error");
        }
        // заполняем dto для клиента
        OutputRoleDto out = new OutputRoleDto();
        out.setId(entity.getId());
        out.setTitle(entity.getTitle());
        return out; // отправляем в контроллер
    }

    /**
     * Метод, создающий роль
     *
     * @param dto входящий dto
     * @return Выводит OutputRoleDto
     * @throws RegConflictException отлов ошибок
     * @throws DBException          отлов ошибок
     */
    public OutputRoleDto created(InputRoleDto dto) throws RegConflictException, DBException {
        // поиск роли по имени
        Optional<RoleEntity> opt = repository.findByTitle(dto.getTitle());
        if (opt.isPresent()) { // роль по имени найдена, тогда кидаем ошибку
            throw new RegConflictException("o.o");
        }
        // заполняем новую сущность
        RoleEntity entity = new RoleEntity();
        entity.setTitle(dto.getTitle());
        try { // сохраняет сущность
            repository.save(entity);
        } catch (DataAccessException e) {
            System.out.println("500 - internal server error \n " + e.getMessage());
            throw new DBException("error");
        }
        // формируем dto для клиента
        OutputRoleDto out = new OutputRoleDto();
        out.setId(entity.getId());
        out.setTitle(entity.getTitle());
        return out; // отправляем в контроллер
    }

    /**
     * Метод, который удаляет роль из базы
     *
     * @param id id роли
     * @return возвращает boolean
     * @throws UserSearchException отлов ошибок
     */
    public boolean delete(Long id) throws UserSearchException {
        // поиск роли
        Optional<RoleEntity> opt = repository.findById(id);
        if (!opt.isPresent()) { // если роль не найдена
            throw new UserSearchException("error");
        }
        RoleEntity role = opt.get(); // получаем сущность
        // поиск связующих записей в user_role
        List<AdminRoleItemEntity> roleItems = roleItemRepository.findByRoleId(role.getId());
        for (AdminRoleItemEntity item : roleItems) { // удаляем эту роль у пользователей
            try { // удаление
                roleItemRepository.delete(item);
            } catch (DataAccessException e) {
                System.out.println("500 - internal server error \n " + e.getMessage());
                throw new DBException("error");
            }
        }
        try { // удаляем саму роль
            repository.delete(role);
        } catch (DataAccessException e) {
            System.out.println("500 - internal server error \n " + e.getMessage());
            throw new DBException("error");
        }
        return true;
    }


}
