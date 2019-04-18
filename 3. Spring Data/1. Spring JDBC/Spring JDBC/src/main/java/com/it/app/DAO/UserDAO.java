package com.it.app.DAO;

import com.it.app.model.User;

import java.util.List;

interface UserDao {

    User findOne(Long id);

    User save(User user);

    List<User> findAll();

    int update(User user);

    int[] batchUpdate(List<User> userList);

    int delete(User user);

}
