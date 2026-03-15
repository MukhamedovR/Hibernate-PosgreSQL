package dao;

import entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserDaoTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private static SessionFactory sessionFactory;
    private UserDao userDao;

    @BeforeAll
    static void beforeAll() {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterAll
    static void afterAll() {
        if (sessionFactory != null) sessionFactory.close();
    }

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl(sessionFactory);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createMutationQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    @DisplayName("Сохранение и поиск пользователя по ID")
    void saveAndGetById_Success() {
        User user = new User("Tester", "test@mail.com", 30);

        userDao.save(user);

        assertNotNull(user.getId());
        User found = userDao.getById(user.getId());
        assertNotNull(found);
        assertEquals("Tester", found.getName());
        assertEquals("test@mail.com", found.getEmail());
    }

    @Test
    @DisplayName("Получение списка всех пользователей")
    void getAll_ShouldReturnAllUsers() {
        userDao.save(new User("User1", "u1@mail.com", 20));
        userDao.save(new User("User2", "u2@mail.com", 25));

        List<User> users = userDao.getAll();

        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Обновление пользователя")
    void update_ShouldChangeUserData() {
        User user = new User("OldName", "old@mail.com", 20);
        userDao.save(user);

        user.setName("NewName");
        userDao.update(user);

        User updated = userDao.getById(user.getId());
        assertEquals("NewName", updated.getName());
    }

    @Test
    @DisplayName("Удаление пользователя")
    void delete_ShouldRemoveUser() {
        User user = new User("ToDelete", "del@mail.com", 25);
        userDao.save(user);
        Long id = user.getId();

        userDao.delete(id);

        assertNull(userDao.getById(id));
    }

    @Test
    @DisplayName("Поиск несуществующего пользователя возвращает null")
    void getById_NotFound_ReturnsNull() {
        User found = userDao.getById(999L);
        assertNull(found);
    }
}
