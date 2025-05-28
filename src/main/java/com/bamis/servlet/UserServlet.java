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


public class UserServlet extends HttpServlet {
    UserDao userDao = new UserDao();
    private static final Logger logger = LoggerFactory.getLogger(UserServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        EntityManager em = DatabaseConfig.getEntityManagerFactory().createEntityManager();
        userDao.setEntityManager(em);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Affiche la page de connexion (par exemple, login.jsp)
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        logger.info("Tentative de connexion - Username: " + username + ", Password: " + password);
        User user = userDao.findUser(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/home.jsp");
        } else {
            request.setAttribute("error", "Identifiants incorrects");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }


}
