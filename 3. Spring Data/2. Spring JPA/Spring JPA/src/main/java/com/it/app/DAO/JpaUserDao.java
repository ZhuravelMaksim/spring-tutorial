package com.it.app.DAO;

import com.it.app.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

@Component
public class JpaUserDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Gets persistent entity by id
     *
     * @param id - entity ID
     */
    @Override
    public User findOne(Long id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Deletes persistent user by id
     *
     * @param id - user ID
     */
    @Override
    @Transactional
    public void delete(User user) {
        User entity = entityManager.find(User.class, user.getId());
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    /**
     * Saves an transient entity
     *
     * @param entity - transient entity
     */
    @Override
    @Transactional
    public void save(User entity) {
        entityManager.persist(entity);
    }

    /**
     * Updates a persistent\detached entity
     *
     * @param entity - persistent\detached entity
     * @return User
     */
    @Override
    @Transactional
    public User update(User entity) {
        return entityManager.merge(entity);
    }
}
