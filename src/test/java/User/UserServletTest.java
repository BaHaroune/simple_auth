package User;// src/test/java/com/bamis/servlet/UserServletTest.java


import com.bamis.dao.UserDao;
import com.bamis.model.User;
import com.bamis.servlet.UserServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class UserServletTest {
    private UserServlet servlet;
    private UserDao userDao;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        servlet = new UserServlet();
        userDao = mock(UserDao.class);
        servlet.userDao = userDao; // injection manuelle
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    @Test
    void testDoGet() throws Exception {
        List<User> users = Arrays.asList(new User(), new User());
        when(userDao.findAllUsers()).thenReturn(users);
        when(request.getRequestDispatcher("/home.jsp")).thenReturn(dispatcher);

        servlet.doGet(request, response);

        verify(userDao).findAllUsers();
        verify(request).setAttribute("users", users);
        verify(dispatcher).forward(request, response);
    }
}