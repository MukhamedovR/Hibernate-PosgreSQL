package dao;

import entity.User;

import java.util.List;

public interface UserDao {

    void save(User user);

    User getById(Long id);

    void update(User user);

    void delete(Long id);

    List<User> getAll();
}

