package com.sahibinden.config;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class EntityManagerProducer {

    @Produces
    public EntityManager produceEntityManager() {
        return Persistence
                .createEntityManagerFactory("jettyEmbeddedPU")
                .createEntityManager();
    }
}
