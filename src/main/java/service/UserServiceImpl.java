package service;

import dao.UserDao;
import dto.UserDTO;
import entity.User;
import mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDTO createUser(UserDTO dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }
        if (dto.getAge() < 0 || dto.getAge() > 120) {
            throw new IllegalArgumentException("Указан недопустимый возраст (0-120)");
        }
        User user = UserMapper.toEntity(dto);
        userDao.save(user);
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userDao.getById(id);
        return UserMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userDao.getAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        user.setId(dto.getId());
        userDao.update(user);
        return UserMapper.toDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.delete(id);
    }
}
