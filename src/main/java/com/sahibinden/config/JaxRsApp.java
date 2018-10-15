package com.sahibinden.config;

import com.google.common.collect.ImmutableSet;
import com.sahibinden.controller.GameController;
import com.sahibinden.exception.MyApplicationExceptionHandler;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Set;

@ApplicationPath("/")
public class JaxRsApp extends Application {

    private static final ImmutableSet services = ImmutableSet.of(
            GameController.class,
            MyApplicationExceptionHandler.class
    );


    @Override
    public Set<Class<?>> getClasses() {
        return services;
    }
}
