package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.Category;
import jakarta.persistence.*;

import java.util.Scanner;

public class Delete {
    public static void delete() {
        //удаление категории, все вложенные категории тоже должны удалиться и ключи встать на место тоже

        EntityManagerFactory factory = EntityManagerFactoryHolder.factory();
        EntityManager manager = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите id родительской категорий: ");
        int parentIdToDelete = Integer.parseInt(scanner.nextLine());

        TypedQuery<Category> firstQuery = manager.createQuery(
                "select c from Category c where c.id = ?1", Category.class
        );
        firstQuery.setParameter(1, parentIdToDelete);

        try {
            manager.getTransaction().begin();

            Category parentCategory = firstQuery.getSingleResult();
            int rightKey = parentCategory.getRight_key();
            int leftKey = parentCategory.getLeft_key();
            int range = parentCategory.getRight_key() - parentCategory.getLeft_key();

            Query query = manager.createQuery(
                    "delete from Category c where c.left_key >= ?1 and c.right_key <= ?2"
            );
            query.setParameter(1, leftKey);
            query.setParameter(2, rightKey);
            query.executeUpdate();

            Query query1 = manager.createQuery(
                    "update Category c set c.left_key = (c.left_key - ?1) - 1  where c.left_key > ?2"
            );
            query1.setParameter(1, range);
            query1.setParameter(2, leftKey);
            query1.executeUpdate();

            Query query2 = manager.createQuery(
                    "update Category c set c.right_key = (c.right_key - ?1) - 1 where c.right_key > ?2"
            );
            query2.setParameter(1, range);
            query2.setParameter(2, rightKey);
            query2.executeUpdate();

            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException(e);
        }

    }
}
