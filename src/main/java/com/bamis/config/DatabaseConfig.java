package com.bamis.config;


import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConfig {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("defaultPU");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}

