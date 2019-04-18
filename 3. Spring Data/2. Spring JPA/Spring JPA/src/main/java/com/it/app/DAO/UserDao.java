package com.it.app.DAO;

import com.it.app.model.User;

import java.util.List;

public interface UserDao {

    User findOne(Long id);

    void save(User user);

    User update(User user);

    void delete(User user);
}
