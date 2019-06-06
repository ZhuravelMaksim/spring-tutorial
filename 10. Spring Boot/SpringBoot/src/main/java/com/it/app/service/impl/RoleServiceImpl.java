package com.it.app.service.impl;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.model.Role;
import com.it.app.repository.RoleRepository;
import com.it.app.service.RoleService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    @Cacheable(value = "findAllRoles", unless = "#result.size() == 0")
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Cacheable(value = "findRoleById", key = "#id")
    public Role findById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.role.notExist", new Object[]{})));
    }

    @Override
    @Cacheable(value = "findRoleByName", key = "#name")
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    @Caching(
            put = {@CachePut(value = "findRoleById", key = "#result.id"),
                    @CachePut(value = "findRoleByName", key = "#result.name")},
            evict = {@CacheEvict(value = "findAllRoles", allEntries = true)}
    )
    public Role save(Role role) {
        validate(role.getId() != null, localizedMessageSource.getMessage("error.role.notHaveId", new Object[]{}));
        validate(roleRepository.existsByName(role.getName()), localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    @Override
    @Caching(
            put = {@CachePut(value = "findRoleById", key = "#result.id"),
                    @CachePut(value = "findRoleByName", key = "#result.name")},
            evict = {@CacheEvict(value = "findAllRoles", allEntries = true)}
    )
    public Role update(Role role) {
        final Long id = role.getId();
        validate(id == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        final Role duplicateRole = roleRepository.findByName(role.getName());
        findById(id);
        final boolean isDuplicateExists = duplicateRole != null && !Objects.equals(duplicateRole.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.role.name.notUnique", new Object[]{}));
        return roleRepository.saveAndFlush(role);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "findRoleById", key = "#role.id"),
            @CacheEvict(value = "findRoleByName", key = "#role.name"),
            @CacheEvict(value = "findAllRoles", allEntries = true)}
    )
    public void delete(Role role) {
        final Long id = role.getId();
        validate(id == null, localizedMessageSource.getMessage("error.role.haveId", new Object[]{}));
        findById(id);
        roleRepository.delete(role);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "findRoleById", key = "#id"),
            @CacheEvict(value = "findRoleByName", allEntries = true),
            @CacheEvict(value = "findAllRoles", allEntries = true)}
    )
    public void deleteById(Long id) {
        findById(id);
        roleRepository.deleteById(id);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
