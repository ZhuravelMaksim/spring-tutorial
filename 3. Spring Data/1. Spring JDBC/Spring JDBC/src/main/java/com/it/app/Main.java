package com.it.app;

import com.it.app.DAO.JdbcUserDao;
import com.it.app.config.AppConfiguration;
import com.it.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Main {

    @Autowired
    private JdbcUserDao jdbcUserDao;

    /**
     * Initializing of AnnotationConfigApplicationContext
     * You can have multiple instances of ApplicationContexts, in that case, they will be completely isolated, each with its own configuration.
     *
     * @param args - args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotatedClassApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Main main = annotatedClassApplicationContext.getBean("main", Main.class);
        main.jdbcUserDao.findOne(1L);
        main.jdbcUserDao.findAll();
        main.jdbcUserDao.delete(null);
        User user = new User();
        user.setName("TESTA");
        main.jdbcUserDao.save(user);
        main.jdbcUserDao.update(user);

        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(3L);
        user1.setName("NAME3");
        userList.add(user1);
        User user2 = new User();
        user2.setId(2L);
        user2.setName("NAME2");
        userList.add(user2);
        main.jdbcUserDao.batchUpdate(userList);
        // main.jdbcUserDao.findAll(); remove row 30 if you want to test
    }
}