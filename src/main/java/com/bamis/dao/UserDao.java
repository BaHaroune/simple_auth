package com.bamis.dao;

import com.bamis.model.User;
import com.bamis.servlet.UserServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;

public class UserDao {
    private EntityManager em;
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public void saveUser(String username, String password) {
        em.getTransaction().begin();
        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(hashPassword(password));
            em.persist(user);
            em.getTransaction().commit();
            logger.info("Nouvel utilisateur enregistré : {}", username);
        } catch (Exception e) {
            logger.error("Erreur lors de l'enregistrement de l'utilisateur : {}", username, e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de l'enregistrement de l'utilisateur", e);
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur de hachage du mot de passe", e);
        }
    }


    public User findUser(String username, String password) {
        if (username == null || username.isBlank() || password == null) {
            logger.warn("Attempt to find user with invalid credentials - username: {}", username);
            return null;
        }

        try {
            // First find user by username only
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();


            // Then verify password hash matches
            String hashedPassword = hashPassword(password);
            if (hashedPassword.equals(user.getPassword())) {
                return user;
            }
            return null;

        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }


    public List<User> findAllUsers() {
        logger.debug("Attempting to fetch all users from database");
        try {
            List<User> users = em.createQuery("SELECT u FROM User u", User.class)
                    .getResultList();
            logger.debug("Successfully fetched {} users", users.size());
            if (logger.isTraceEnabled()) {
                for (User user : users) {
                    logger.trace("User found - ID: {}, Username: {}",
                            user.getId(), user.getUsername());
                }
            }
            return users;
        } catch (Exception e) {
            logger.error("Error occurred while fetching all users", e);
            return Collections.emptyList();
        }
    }

    public void deleteUser(Long id) {
        em.getTransaction().begin();
        try {
            User user = em.find(User.class, id);
            if (user != null) {
                em.remove(user);
                logger.info("Utilisateur supprimé avec succès - ID: {}", id);
            } else {
                logger.warn("Aucun utilisateur trouvé avec l'ID: {}", id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Erreur lors de la suppression de l'utilisateur - ID: {}", id, e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur", e);
        }
    }

    public void updateUser(Long id, String username, String password) {
        em.getTransaction().begin();
        try {
            User user = em.find(User.class, id);
            if (user != null) {
                user.setUsername(username);
                user.setPassword(hashPassword(password));
                em.merge(user);
                logger.info("Utilisateur mis à jour avec succès - ID: {}", id);
            } else {
                logger.warn("Aucun utilisateur trouvé avec l'ID: {}", id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de l'utilisateur - ID: {}", id, e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur", e);
        }
    }

    public User findUserById(Long id) {
        User user = em.find(User.class, id);
        return user;
    }

}
