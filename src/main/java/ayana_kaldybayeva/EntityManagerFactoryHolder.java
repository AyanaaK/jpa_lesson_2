package ayana_kaldybayeva;

import jakarta.persistence.*;

public class EntityManagerFactoryHolder {
    private static EntityManagerFactory factory = null;

    public static EntityManagerFactory factory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("main");
        }
        return factory;
    }
}
