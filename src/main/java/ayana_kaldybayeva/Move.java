package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.Category;
import jakarta.persistence.*;

import java.util.Scanner;

public class Move {
    public static void main(String[] args) {
        // Введите id перемещаемой категории: 2
        // Введите id новой родительской категории: 7

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите id перемещаемой категорий: ");
        int idToMove = Integer.parseInt(scanner.nextLine());

        TypedQuery<Category> query = manager.createQuery(
                "select c from Category c where c.id = ?1", Category.class
        );
        query.setParameter(1, idToMove);

        System.out.println("Введите id новой родительской категорий: ");
        int idNewParent = Integer.parseInt(scanner.nextLine());

        TypedQuery<Category> query1 = manager.createQuery(
                "select c from Category c where c.id = ?1", Category.class
        );
        query1.setParameter(1, idNewParent);

        try {
            manager.getTransaction().begin();


            Category firstParentCategory = query.getSingleResult();
            int leftKey = firstParentCategory.getLeft_key();
            int rightKey = firstParentCategory.getRight_key();
            int range = firstParentCategory.getRight_key() - firstParentCategory.getLeft_key();

            Category secondParentCategory = query1.getSingleResult();
            int rightKeyOfSecondParent = secondParentCategory.getRight_key();
            int leftKeyOfSecondParent = secondParentCategory.getLeft_key();

            if (!(leftKey <= leftKeyOfSecondParent && rightKey >= rightKeyOfSecondParent)) {
                // 1) Сделать отрицательными ключи перемещаемой категории.
                Query query2 = manager.createQuery(
                        "update Category c set c.left_key = -c.left_key where c.left_key >= ?1 and c.right_key <= ?2"
                );
                query2.setParameter(1, leftKey);
                query2.setParameter(2, rightKey);
                query2.executeUpdate();

                Query query3 = manager.createQuery(
                        "update Category c set c.right_key = -c.right_key where c.right_key >= ?1 and c.right_key <= ?2"
                );
                query3.setParameter(1, leftKey);
                query3.setParameter(2, rightKey);
                query3.executeUpdate();

                // 2) Убрать образовавшийся промежуток.
                Query query4 = manager.createQuery(
                        "update Category c set c.left_key = (c.left_key - ?1) - 1 where c.left_key > ?2"
                );
                query4.setParameter(1, range);
                query4.setParameter(2, leftKey);
                query4.executeUpdate();

                Query query5 = manager.createQuery(
                        "update Category c set c.right_key = (c.right_key - ?1) - 1 where c.right_key > ?2"
                );
                query5.setParameter(1, range);
                query5.setParameter(2, rightKey);
                query5.executeUpdate();

                // 3) Выделить место в новой родительской категории.

                manager.refresh(secondParentCategory);

                Query query6 = manager.createQuery(
                        "update Category c set c.left_key = (c.left_key + ?1) + 1 where c.left_key >= ?2"
                );
                query6.setParameter(1, range);
                query6.setParameter(2, secondParentCategory.getRight_key());
                query6.executeUpdate();

                Query query7 = manager.createQuery(
                        "update Category c set c.right_key = (c.right_key + ?1) + 1 where c.right_key >= ?2"
                );
                query7.setParameter(1, range);
                query7.setParameter(2, secondParentCategory.getRight_key());
                query7.executeUpdate();


                // -2 -7 -> 6 17 -> 11 16
                // -3 -4            12 13
                // -5 -6            14 15

                // 0 - (<актуальный ключ в таблице>) + (<правый ключ нового родителя> - <правый ключ основной перемещаемой> - 1)

                // 0 - (-2) + (17 - 7 - 1) = 11
                // 0 - (-6) + (17 - 7 - 1) = 15


                //4) изменение уровня вложенности
                // 4 -> 25 -> 26
                // 5          27

                // 4 - 4 + 25 + 1 = 26
                // 5 - 4 + 25 + 1 = 27

                // 25 -> 4 -> 5
                // 26         6

                // 25 - 25 + 4 + 1 = 5
                // 26 - 25 + 4 + 1 = 6

                manager.refresh(secondParentCategory);

                int firstDepth = firstParentCategory.getDepth();
                int secondDepth = secondParentCategory.getDepth();
                Query query10 = manager.createQuery(
                        "update Category c set c.depth = c.depth - ?1 + ?2 + 1 where c.left_key < 0 and c.right_key < 0"
                );
                query10.setParameter(1, firstDepth);
                query10.setParameter(2, secondDepth);
                query10.executeUpdate();

                // 5) Перемещение категории.
                int formula = secondParentCategory.getRight_key() - rightKey - 1;
                Query query8 = manager.createQuery(
                        "update Category c set c.left_key = 0 - (c.left_key) + (?1) where c.left_key < 0"
                );
                query8.setParameter(1, formula);
                query8.executeUpdate();

                Query query9 = manager.createQuery(
                        "update Category c set c.right_key = 0 - (c.right_key) + (?1) where  c.right_key < 0"
                );
                query9.setParameter(1, formula);
                query9.executeUpdate();
            } else {
                System.out.println("Impossible");
            }


            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
