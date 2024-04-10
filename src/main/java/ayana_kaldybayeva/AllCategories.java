package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.Category;
import jakarta.persistence.*;

import java.util.List;

public class AllCategories {
    public static void all() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        TypedQuery<Category> query = manager.createQuery(
                "select c from Category c order by left_key", Category.class
        );
        List<Category> categories = query.getResultList();

        for (Category category : categories) {
            StringBuilder line = new StringBuilder("");
            for (int i = 0; i < category.getDepth(); i++) {
                line.append("- ");
            }
            System.out.println(line + category.getName());
        }
    }
}