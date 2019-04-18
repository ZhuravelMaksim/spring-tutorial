package com.it.app.service.impl;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.model.Role;
import com.it.app.repository.RoleRepository;
import com.it.app.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final LocalizedMessageSource localizedMessageSource;

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository, LocalizedMessageSource localizedMessageSource) {
        this.roleRepository = roleRepository;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.role.notExist", new Object[]{})));
    }

    @Override
    public Role save(Role role) {
        validate(role.getId() != null, localizedMessageSource.getMessage("error.role.notHaveId", new Object[]{}));
        validate(roleRepository.existsByName(role.getName()), localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role update(Role role) {
        final Long roleId = role.getId();
        validate(roleId == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        final Role duplicateRole = roleRepository.findByName(role.getName());
        final boolean isDuplicateExists = duplicateRole != null && !Objects.equals(duplicateRole.getId(), roleId);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public void delete(Role role) {
        validate(role.getId() == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        roleRepository.delete(role);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
