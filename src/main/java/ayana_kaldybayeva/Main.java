package ayana_kaldybayeva;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Что вы хотите сделать? Выберите номер");
            System.out.println("1. Создать категорию");
            System.out.println("2. Удалить категорию");
            System.out.println("3. Переместить категорию");
            System.out.println("4. Перечислить все категории");
            System.out.println("5. Перечислить подкатегории выбранной категорий");
            System.out.println("6. Выйти");

            System.out.print("Номер: ");

            Scanner scanner = new Scanner(System.in);
            int actionToDo = Integer.parseInt(scanner.nextLine());

            switch (actionToDo) {
                case 1:
                    Create.create();
                    break;
//                case 2:
//                    Delete.delete();
//                    break;
//                case 3:
//                    Move.move();
//                    break;
//                case 4:
//                    AllCategories.all();
//                    break;
//                case 5:
//                    ListSubcategories.subcategories();
//                    break;
                case 6:
                    System.out.println("Вы вышли из программы");
                    return;
                default:
                    System.out.println("Неверный номер действия. Попробуйте заново");
                    break;
            }
        }
    }
}
