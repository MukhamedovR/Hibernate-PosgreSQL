package service;

import dao.UserDao;
import dto.UserDTO;
import entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Успешная регистрация пользователя")
    void createUser_Success() {
        UserDTO dto = new UserDTO(null, "Alice", "alice@mail.com", 25);

        UserDTO result = userService.createUser(dto);

        verify(userDao, times(1)).save(any(User.class));
        assertNotNull(result);
        assertEquals("Alice", result.getName());
    }

    @Test
    @DisplayName("Ошибка валидации — имя пустое")
    void createUser_EmptyName_ThrowsException() {
        UserDTO dto = new UserDTO(null, "", "test@mail.com", 25);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(dto));
        verify(userDao, never()).save(any());
    }

    @Test
    @DisplayName("Ошибка валидации — возраст недопустим")
    void createUser_InvalidAge_ThrowsException() {
        UserDTO dto = new UserDTO(null, "Bob", "bob@mail.com", 150);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(dto));
        verify(userDao, never()).save(any());
    }

    @Test
    @DisplayName("Получение пользователя по ID")
    void getUserById_Success() {
        User user = new User("Alice", "alice@mail.com", 25);
        user.setId(1L);
        when(userDao.getById(1L)).thenReturn(user);

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        verify(userDao, times(1)).getById(1L);
    }

    @Test
    @DisplayName("Получение несуществующего пользователя возвращает null")
    void getUserById_NotFound_ReturnsNull() {
        when(userDao.getById(999L)).thenReturn(null);

        UserDTO result = userService.getUserById(999L);

        assertNull(result);
    }

    @Test
    @DisplayName("Получение всех пользователей")
    void getAllUsers_Success() {
        when(userDao.getAll()).thenReturn(List.of(
                new User("User1", "u1@mail.com", 20),
                new User("User2", "u2@mail.com", 30)
        ));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userDao, times(1)).getAll();
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUser_Success() {
        userService.deleteUser(1L);

        verify(userDao, times(1)).delete(1L);
    }

    @Test
    @DisplayName("Обновление пользователя")
    void updateUser_Success() {
        UserDTO dto = new UserDTO(1L, "Updated", "upd@mail.com", 30);

        userService.updateUser(dto);

        verify(userDao, times(1)).update(any(User.class));
    }
}
