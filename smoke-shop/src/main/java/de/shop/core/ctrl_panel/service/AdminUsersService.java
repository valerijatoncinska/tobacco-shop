package de.shop.core.ctrl_panel.service;

import de.shop.core.ctrl_panel.domain.dto.OutputRoleDto;
import de.shop.core.ctrl_panel.domain.dto.OutputUserDataDto;
import de.shop.core.ctrl_panel.domain.dto.OutputUserDto;
import de.shop.core.ctrl_panel.domain.dto.OutputUserPreviewDto;
import de.shop.core.ctrl_panel.domain.entity.AdminRoleItemEntity;
import de.shop.core.ctrl_panel.repository.AdminRoleItemRepository;
import de.shop.core.ctrl_panel.repository.AdminRoleRepository;
import de.shop.core.ctrl_panel.repository.AdminUsersRepository;
import de.shop.core.exceptions.UserSearchException;
import de.shop.modules.users.domain.entity.RoleEntity;
import de.shop.modules.users.domain.entity.UserEntity;
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
     * Метод выводит информацию о конкретном пользователе
     * @param id id пользователя
     * @return выводит OutputUserDto
     * @throws UserSearchException перехвад ошибок
     */
    public OutputUserDto info(Long id) throws UserSearchException {
        Optional<UserEntity> opt = repository.findById(id); // поиск пользователя
        if (!opt.isPresent()) {
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


        OutputUserDto output = new OutputUserDto();
        output.setData(data);
        output.setRoles(l);
        return output;
    }

    public List<OutputUserPreviewDto> list() {
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
