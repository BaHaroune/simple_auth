// src/test/java/com/bamis/dao/UserDaoTest.java
package User;

import com.bamis.dao.UserDao;
import com.bamis.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDaoTest {
    private UserDao userDao;
    private EntityManager em;
    private TypedQuery<User> query;

    @BeforeEach
    void setUp() {
        userDao = new UserDao();
        em = mock(EntityManager.class);
        query = mock(TypedQuery.class);
        userDao.setEntityManager(em);
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("alice");
        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("bob");
        List<User> expectedUsers = Arrays.asList(user1, user2);

        when(em.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedUsers);

        List<User> result = userDao.findAllUsers();

        assertEquals(2, result.size());
        assertEquals("alice", result.get(0).getUsername());
        assertEquals("bob", result.get(1).getUsername());
    }
}