package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.Category;
import jakarta.persistence.*;

import java.util.Scanner;

public class Create {

    public static void create() {
        // Введите id родительской категории: 2
        // Введите название новой категории: МЦСТ

        // Все ключи которые больше либо равны правому ключу роительской категории должны быть увеличины на 2.

        EntityManagerFactory factory = EntityManagerFactoryHolder.factory();
        EntityManager manager = factory.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите id родительской категорий: ");
        int parentID = Integer.parseInt(scanner.nextLine());

        TypedQuery<Category> firstQuery = manager.createQuery(
                "select c from Category c where c.id = ?1", Category.class
        );
        firstQuery.setParameter(1, parentID);

        System.out.println("Введите название категории: ");
        String newCategory = scanner.nextLine();

        try {
            manager.getTransaction().begin();

            Category parentCategory = firstQuery.getSingleResult();
            int rightKey = parentCategory.getRight_key();

            Query query = manager.createQuery(
                    "update Category c set c.left_key = c.left_key + 2 where c.left_key >= ?1"
            );
            query.setParameter(1, rightKey);
            query.executeUpdate();

            Query query1 = manager.createQuery(
                    "update Category c set c.right_key = c.right_key + 2 where c.right_key >= ?1"
            );
            query1.setParameter(1, rightKey);
            query1.executeUpdate();

            Category category = new Category();
            category.setName(newCategory);
            category.setLeft_key(5);
            category.setRight_key(6);
            category.setDepth(0);
            manager.persist(category);

            manager.getTransaction().commit();
        } catch (RuntimeException runtimeException) {
            manager.getTransaction().rollback();
            throw new RuntimeException(runtimeException);
        }

    }
}
