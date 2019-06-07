package com.it.app.service;

import com.it.app.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User findByName(String name);

    User save(User user);

    User update(User user);

    void delete(User user);

    void deleteById(Long id);

}
