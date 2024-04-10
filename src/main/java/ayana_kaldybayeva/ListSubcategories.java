package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.Category;
import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

public class ListSubcategories {
    public static void subcategories() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите название категорий: ");
        String categoryName = scanner.nextLine();

        TypedQuery<Category> firstQuery = manager.createQuery(
                "select c from Category c where c.name = ?1 order by left_key", Category.class
        );

        firstQuery.setParameter(1, categoryName);

        try {
            Category parentCategory = firstQuery.getSingleResult();
            int leftKey = parentCategory.getLeft_key();
            int rightKey = parentCategory.getRight_key();

            TypedQuery<Category> query = manager.createQuery(
                    "select c from Category c where c.left_key >= ?1 and c.right_key <= ?2",
                    Category.class
            );
            query.setParameter(1, leftKey);
            query.setParameter(2, rightKey);
            List<Category> categories = query.getResultList();

            for (Category category : categories) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < category.getDepth(); i++) {
                    line.append("- ");
                }
                System.out.println(line + category.getName());
            }
        } catch (NoResultException noResultException) {
            System.out.println("Такой категории нет");
        }

    }
}
