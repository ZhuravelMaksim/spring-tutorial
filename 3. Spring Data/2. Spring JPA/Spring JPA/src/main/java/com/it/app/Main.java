package com.it.app;

import com.it.app.DAO.UserDao;
import com.it.app.config.AppConfiguration;
import com.it.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main {

    @Autowired
    private UserDao jpaUserDao;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotatedClassApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        Main main = annotatedClassApplicationContext.getBean("main", Main.class);
        User one = main.getJpaUserDao().findOne(1L);
        System.out.println(one);

        main.getJpaUserDao().delete(one);
        System.out.println(main.getJpaUserDao().findOne(1L));

        User user = new User();
        user.setName("TESTA");
        main.getJpaUserDao().save(user);
        System.out.println(main.getJpaUserDao().findOne(3L));

        user.setName("TESTB");
        main.getJpaUserDao().update(user);
        System.out.println(main.getJpaUserDao().findOne(3L));
    }

    public UserDao getJpaUserDao() {
        return jpaUserDao;
    }

    public void setJpaUserDao(UserDao jpaUserDao) {
        this.jpaUserDao = jpaUserDao;
    }

}