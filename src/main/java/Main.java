import controller.UserController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- МЕНЮ ---");
            System.out.println("1. Создать пользователя\n2. Найти по ID\n3. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            if (choice == 3) break;

            if (choice == 1) userController.createUser();
            else if (choice == 2) userController.findUser();
        }
        System.out.println("Программа завершена.");
    }
}
