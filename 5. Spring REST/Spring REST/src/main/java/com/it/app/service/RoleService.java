package com.it.app.service;

import com.it.app.model.Role;
import com.it.app.model.User;

import java.util.List;
import java.util.Locale;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);

    Role save(Role role);

    Role update(Role role);

    void delete(Role role);

    void deleteById(Long id);

}
