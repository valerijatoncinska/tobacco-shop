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

@Service
public class AdminRoleService {
    private AdminRoleRepository repository;
    private AdminRoleItemRepository roleItemRepository;

    public AdminRoleService(AdminRoleRepository repository, AdminRoleItemRepository roleItemRepository) {
        this.repository = repository;
        this.roleItemRepository = roleItemRepository;
    }

    public List<OutputRoleDto> list() {
        return repository.findAll().stream()
                .map(entity -> {
                    OutputRoleDto out = new OutputRoleDto();
                    out.setId(entity.getId());
                    out.setTitle(entity.getTitle());
                    return out;
                }).toList();
    }

    public OutputRoleDto update(Long id, InputRoleDto dto) throws RegConflictException, DBException, UserSearchException {
        Optional<RoleEntity> opt = repository.findById(id);
        RoleEntity entity = opt.get();
        if (!opt.isPresent()) {
            throw new UserSearchException("o.o");
        }
        Optional<RoleEntity> optTitle = repository.findByTitle(dto.getTitle());
        if (optTitle.isPresent()) {
            RoleEntity entityTitle = optTitle.get();
            if (entityTitle.getId() != entity.getId()) {
                throw new RegConflictException("error");
            }
        }

        entity.setTitle(dto.getTitle());
        try {
            repository.save(entity);
        } catch (DataAccessException e) {
            System.out.println("500 - internal server error \n " + e.getMessage());
            throw new DBException("error");
        }
        OutputRoleDto out = new OutputRoleDto();
        out.setId(entity.getId());
        out.setTitle(entity.getTitle());
        return out;
    }

    public OutputRoleDto created(InputRoleDto dto) throws RegConflictException, DBException {
        Optional<RoleEntity> opt = repository.findByTitle(dto.getTitle());
        if (opt.isPresent()) {
            throw new RegConflictException("o.o");
        }
        RoleEntity entity = new RoleEntity();
        entity.setTitle(dto.getTitle());
        try {
            repository.save(entity);
        } catch (DataAccessException e) {
            System.out.println("500 - internal server error \n " + e.getMessage());
            throw new DBException("error");
        }
        OutputRoleDto out = new OutputRoleDto();
        out.setId(entity.getId());
        out.setTitle(entity.getTitle());
        return out;
    }

    public boolean delete(Long id) throws UserSearchException {
        Optional<RoleEntity> opt = repository.findById(id);
        if (!opt.isPresent()) {
            throw new UserSearchException("error");
        }
        RoleEntity role = opt.get();
        List<AdminRoleItemEntity> roleItems = roleItemRepository.findByRoleId(role.getId());
        for (AdminRoleItemEntity item : roleItems) {
            try {
                roleItemRepository.delete(item);
            } catch (DataAccessException e) {
                System.out.println("500 - internal server error \n " + e.getMessage());
                throw new DBException("error");
            }
        }
        try {
            repository.delete(role);
        } catch(DataAccessException e) {
            System.out.println("500 - internal server error \n "+e.getMessage());
            throw new DBException("error");
        }
return true;
    }


}
