package de.shop.core.ctrl_panel.service;

import de.shop.core.ctrl_panel.domain.dto.*;
import de.shop.core.ctrl_panel.domain.entity.AdminRoleItemEntity;
import de.shop.core.ctrl_panel.repository.AdminRoleItemRepository;
import de.shop.core.ctrl_panel.repository.AdminRoleRepository;
import de.shop.core.ctrl_panel.repository.AdminUsersRepository;
import de.shop.core.exceptions.DBException;
import de.shop.core.exceptions.RegConflictException;
import de.shop.core.exceptions.UserSearchException;
import de.shop.modules.users.domain.entity.RoleEntity;
import de.shop.modules.users.domain.entity.UserEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис по управлению пользователями в сайте
 */
@Service
public class AdminUsersService {
    private AdminUsersRepository repository; // репозиторий пользователей
    private AdminRoleRepository roleRepository; // репозиторий ролей
    private AdminRoleItemRepository roleItemRepository; // репозиторий связи ролей и пользователей

    public AdminUsersService(AdminUsersRepository repository, AdminRoleRepository roleRepository, AdminRoleItemRepository roleItemRepository) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.roleItemRepository = roleItemRepository;
    }

    /**
     * Удаление роли у конкретного пользователя
     *
     * @param id     id пользователя
     * @param roleid id связующей записи в user_role
     * @return возвращает OutputUserDto
     * @throws DBException         отлов ошибок
     * @throws UserSearchException отлов ошибок
     */
    public OutputUserDto dropRole(Long id, Long roleid) throws DBException, UserSearchException {
        // поиск связующей записи
        Optional<AdminRoleItemEntity> opt = roleItemRepository.findByUserIdAndId(id, roleid);
        if (!opt.isPresent()) { // запись не найдена
            throw new UserSearchException("not role");
        }
        AdminRoleItemEntity item = opt.get(); // получаем сущность
        try { // удаляем
            roleItemRepository.delete(item);
        } catch (DataAccessException e) {
            System.out.println("500 - internal server error \n " + e.getMessage());
            throw new DBException("Internal server error");
        }
        return info(id); // отправляем данные в контроллер
    }

    /**
     * Добавляем роль
     *
     * @param id  id пользователя
     * @param dto входящий dto
     * @return возвращает OutputUserDto
     * @throws UserSearchException  отлов ошибок
     * @throws RegConflictException отлов ошибок
     */
    public OutputUserDto addRole(Long id, InputRoleIdDto dto) throws UserSearchException, RegConflictException {
        // поиск связующей записи в user_role
        Optional<AdminRoleItemEntity> opt = roleItemRepository.findByUserIdAndRoleId(id, dto.getRole());


        if (opt.isPresent()) { // запись найдена, кидаем ошибку.
            throw new RegConflictException("error");
        }
// поиск роли в базе
        Optional<RoleEntity> optRole = roleRepository.findById(dto.getRole());
        if (!optRole.isPresent()) { // роль не найдена
            throw new UserSearchException("not role");
        }
// Заполняем сущность
        AdminRoleItemEntity item = new AdminRoleItemEntity();
        item.setUserId(id);
        item.setRoleId(dto.getRole());
        try { // Добавляем
            roleItemRepository.save(item);
        } catch (DataAccessException e) {
            System.out.println("500 - internal server error \n " + e.getMessage());
            throw new DBException("error");
        }
        return info(id); // вернули ответ
    }

    /**
     * Метод выводит информацию о конкретном пользователе
     *
     * @param id id пользователя
     * @return выводит OutputUserDto
     * @throws UserSearchException перехвад ошибок
     */
    public OutputUserDto info(Long id) throws UserSearchException {
        // поиск пользователя
        Optional<UserEntity> opt = repository.findById(id); // поиск пользователя
        if (!opt.isPresent()) { // пользователь не найден
            throw new UserSearchException("not user");
        }
        UserEntity entity = opt.get(); // получение пользователя
        // Заполняем информацией DTO
        OutputUserDataDto data = new OutputUserDataDto();
        data.setId(entity.getId());
        data.setEmail(entity.getEmail());
        data.setTimeReg(entity.getTimeReg());
        data.setTimeVisit(entity.getTimeVisit());
        data.setIsAdult(entity.getIsAdult());
        data.setSubscribeNews(entity.getSubscribeNews());
        data.setEmailActive(entity.getEmailActive());
        // собираем роли
        List<OutputRoleDto> l = roleItemRepository.findByUserId(entity.getId()).stream()
                .map(item -> {
                    OutputRoleDto dto = new OutputRoleDto();
                    dto.setId(item.getId());
                    Optional<RoleEntity> roleOpt = roleRepository.findById(item.getRoleId());
                    if (!roleOpt.isPresent()) {
                        throw new UserSearchException("not role");
                    }
                    dto.setTitle(roleOpt.get().getTitle());
                    return dto;
                }).toList();

// формируем dto для клиента
        OutputUserDto output = new OutputUserDto();
        output.setData(data); // данные о пользователе
        output.setRoles(l); // роли пользователя
        return output; // отправляем в контроллер
    }

    /**
     * Метод, выводит список пользователей
     *
     * @return возвращает List<OutputUserPreviewDto>
     */
    public List<OutputUserPreviewDto> list() {
        // прогоняем через стрим
        return repository.findAll().stream()
                .map(user -> {
                    OutputUserPreviewDto out = new OutputUserPreviewDto();
                    out.setId(user.getId());
                    out.setEmail(user.getEmail());
                    System.out.println(user.getTimeVisit());
                    out.setTimeVisit(user.getTimeVisit());
                    return out;
                }).toList();
    }


}
