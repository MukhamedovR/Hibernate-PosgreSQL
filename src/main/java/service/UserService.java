package service;

import dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO dto);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserDTO dto);

    void deleteUser(Long id);
}
