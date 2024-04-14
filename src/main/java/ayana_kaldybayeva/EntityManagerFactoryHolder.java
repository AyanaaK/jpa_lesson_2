package ayana_kaldybayeva;

import jakarta.persistence.*;

public class EntityManagerFactoryHolder {
    private static EntityManagerFactory factory = null;
//    private static EntityManager manager = null;

    public static EntityManagerFactory factory() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("main");
        }
        return factory;
    }

//    public static EntityManager manager() {
//        if (manager == null){
//            manager = factory().createEntityManager();
//        }
//        return manager;
//    }
}
