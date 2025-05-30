package com.bamis.servlet;

import com.bamis.config.DatabaseConfig;
import com.bamis.dao.UserDao;
import com.bamis.model.User;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


public class UserServlet extends HttpServlet {
    public UserDao userDao = new UserDao();
    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        EntityManager em = DatabaseConfig.getEntityManagerFactory().createEntityManager();
        userDao.setEntityManager(em);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        String action = request.getParameter("action");

        if ("ajout".equals(action)) {
            request.getRequestDispatcher("/addUser.jsp").forward(request, response);
            return;
        } else if ("delete".equals(request.getParameter("action"))) {
            Long id = Long.valueOf(request.getParameter("id"));
            userDao.deleteUser(id);

            List<User> users = userDao.findAllUsers();
            request.setAttribute("users", users);

            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        } else if ("edit".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                Long id = Long.valueOf(idParam);
                User editUser = userDao.findUserById(id);
                if (editUser != null) {
                    request.setAttribute("editUser", editUser);
                    request.getRequestDispatcher("/editUser.jsp").forward(request, response);
                    return;
                }
            }
            request.setAttribute("error", "Utilisateur non trouvé");
            request.getRequestDispatcher("/home.jsp").forward(request, response);
            return;
        } else if ("logout".equals(action)) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Cas par défaut : afficher les utilisateurs
        List<User> users = userDao.findAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if ("add".equals(request.getParameter("action"))) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username == null || username.isBlank() || password == null || password.isBlank()) {
                request.setAttribute("error", "Veuillez remplir tous les champs");
                request.getRequestDispatcher("/home.jsp").forward(request, response);
                return;
            }
            try {
                userDao.saveUser(username, password);
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } catch (Exception e) {
                request.setAttribute("error", "Erreur lors de l'enregistrement de l'utilisateur");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }
            return;
        } // Corrige le bloc "update" dans doPost
        else if ("update".equals(request.getParameter("action"))) {
            String idParam = request.getParameter("id");
            if (idParam == null) {
                request.setAttribute("error", "ID utilisateur manquant");
                request.getRequestDispatcher("/home.jsp").forward(request, response);
                return;
            }
            Long id = Long.valueOf(idParam);
            User editedUser = userDao.findUserById(id);
            if (editedUser == null) {
                request.setAttribute("error", "Utilisateur non trouvé");
                request.getRequestDispatcher("/home.jsp").forward(request, response);
                return;
            }
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            editedUser.setUsername(username);
            editedUser.setPassword(password);
            userDao.updateUser(editedUser.getId(), editedUser.getUsername(), editedUser.getPassword());
            request.setAttribute("edited", editedUser);
            response.sendRedirect(request.getContextPath() + "/home.jsp");
            return;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        logger.info("Tentative de connexion - Username: " + username + ", Password: " + password);
        User user = userDao.findUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/login");  // Passe par le servlet
        } else {
            request.setAttribute("error", "Identifiants incorrects");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }
}
