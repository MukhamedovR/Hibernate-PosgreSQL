package controller;

import dao.UserDao;
import dao.UserDaoImpl;
import dto.UserDTO;
import entity.User;
import exception.DatabaseException;
import exception.ValidationException;
import mapper.UserMapper;

import java.util.Scanner;

public class UserController {

    private final UserDao userDao = new UserDaoImpl();
    private final Scanner scanner = new Scanner(System.in);

    public void createUser() {
        try {
            UserDTO dto = new UserDTO();

            System.out.print("Введите имя: ");
            String name = scanner.nextLine();
            if (name == null || name.trim().isEmpty()) {
                throw new ValidationException("Имя пользователя не может быть пустым.");
            }
            dto.setName(name);

            System.out.print("Введите email: ");
            dto.setEmail(scanner.nextLine());

            System.out.print("Введите возраст: ");
            int age = scanner.nextInt();
            scanner.nextLine();
            if (age < 0 || age > 120) {
                throw new ValidationException("Указан недопустимый возраст (0-120).");
            }
            dto.setAge(age);

            User userEntity = UserMapper.toEntity(dto);
            userDao.save(userEntity);

            System.out.println("Пользователь успешно создан.");

        } catch (ValidationException e) {
            System.err.println("Ошибка валидации: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Ошибка базы данных: " + e.getMessage());
        }
    }

    public void findUser() {
        try {
            System.out.print("Введите ID для поиска: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            User userEntity = userDao.getById(id);
            UserDTO dto = UserMapper.toDTO(userEntity);

            if (dto != null) {
                System.out.println("Найден пользователь: ID=" + dto.getId() +
                        ", Имя=" + dto.getName() + ", Email=" + dto.getEmail());
            } else {
                System.out.println("Пользователь с таким ID не найден.");
            }
        } catch (DatabaseException e) {
            System.err.println("Ошибка при поиске: " + e.getMessage());
        }
    }
}