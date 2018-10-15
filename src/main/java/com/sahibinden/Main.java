package com.sahibinden;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.weld.environment.servlet.Listener;

public class Main {

    public static void main(String[] args) throws Exception {

        final Server server = new Server(8080);

        final ServletContextHandler context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.setContextPath("/");

        // CDI (Important to call before RESTEASY)
        context.addEventListener(new Listener());

        // RESTEASY
        ServletHolder h = new ServletHolder(new HttpServletDispatcher());
        h.setInitParameter("javax.ws.rs.Application", "com.sahibinden.config.JaxRsApp");
        h.setInitParameter("resteasy.injector.factory", "org.jboss.resteasy.cdi.CdiInjectorFactory");
        context.addServlet(h, "/*");

        server.setHandler(context);
        server.start();
        server.join();
        
    }
}
