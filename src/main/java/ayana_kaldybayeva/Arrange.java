package ayana_kaldybayeva;

import ayana_kaldybayeva.entity.Category;
import jakarta.persistence.*;

import java.util.List;

public class Arrange {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        EntityManager manager = factory.createEntityManager();

        TypedQuery<Category> query = manager.createQuery(
                "select c from Category c", Category.class
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