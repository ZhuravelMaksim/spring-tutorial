package com.it.app.service;

import com.it.app.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(Long id);

    Role findByName(String name);

    Role save(Role role);

    Role update(Role role);

    void delete(Role role);

    void deleteById(Long id);

}
