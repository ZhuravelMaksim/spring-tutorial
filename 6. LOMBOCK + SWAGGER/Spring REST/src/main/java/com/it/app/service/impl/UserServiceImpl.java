package com.it.app.service.impl;

import com.it.app.component.LocalizedMessageSource;
import com.it.app.model.User;
import com.it.app.repository.UserRepository;
import com.it.app.service.RoleService;
import com.it.app.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final LocalizedMessageSource localizedMessageSource;

    private final RoleService roleService;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, LocalizedMessageSource localizedMessageSource) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(localizedMessageSource.getMessage("error.user.notExist", new Object[]{})));
    }

    @Override
    public User save(User user) {
        validate(user.getId() != null, localizedMessageSource.getMessage("error.user.notHaveId", new Object[]{}));
        validate(userRepository.existsByName(user.getName()), localizedMessageSource.getMessage("error.user.name.notUnique", new Object[]{}));
        return saveAndFlush(user);
    }

    @Override
    public User update(User user) {
        final Long id = user.getId();
        validate(id == null, localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        final User duplicateUser = userRepository.findByName(user.getName());
        final boolean isDuplicateExists = duplicateUser != null && !Objects.equals(duplicateUser.getId(), id);
        validate(isDuplicateExists, localizedMessageSource.getMessage("error.user.name.notUnique", new Object[]{}));
        return saveAndFlush(user);
    }


    @Override
    public void delete(User user) {
        validate(user.getId() == null, localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private User saveAndFlush(User user) {
        validate(user.getRole() == null || user.getRole().getId() == null, localizedMessageSource.getMessage("error.user.role.isNull", new Object[]{}));
        user.setRole(roleService.findById(user.getRole().getId()));
        return userRepository.saveAndFlush(user);
    }

    private void validate(boolean expression, String errorMessage) {
        if (expression) {
            throw new RuntimeException(errorMessage);
        }
    }
}
