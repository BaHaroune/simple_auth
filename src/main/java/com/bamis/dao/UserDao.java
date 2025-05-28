package com.bamis.dao;

import com.bamis.model.User;
import com.bamis.servlet.UserServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UserDao {
    private EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public void saveUser(User user) {
        // Logic to save user to the database
    }

    public User findUser(String username, String password) {
        logger.info("-------------------findUser: " + username + ", " + password);
        try {
            var users = em.createQuery(
                            "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getResultList();
            logger.info("---------------------Utilisateurs trouvés: " + users.size());
            for (User u : users) {
                logger.info("-----------------------Utilisateur: " + u.getUsername());
            }
            return users.isEmpty() ? null : users.get(0);
        } catch (Exception e) {
            logger.error("------------------------Erreur lors de la recherche d'utilisateur", e);
            return null;
        }
    }
}
