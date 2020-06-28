package com.backend.ecommerce.service;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServerInit {
    private static String BASE_URI = null;

    static {
        try {
            BASE_URI = "http://localhost:" + getConfig().getProperty("server.port");
        } catch (IOException e) {
            System.out.println("[BackendECommerce - ERROR] - Could not create settings file!");
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("[BackendECommerce] - Starting server!");
        final HttpServer server = startServer();

        System.out.println("[BackendECommerce] - Server started successfully - " + BASE_URI);
        System.in.read();
        server.shutdown();
    }

    private static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("com.backend.ecommerce.service");
        final Map initParams = new HashMap();

        initParams.put("jersey.config.server.provider.classnames","org.glassfish.jersey.jackson.JacksonFeature");

        rc.addProperties(initParams);

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static Properties getConfig() throws IOException {
        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("conf.properties"));
            prop.getProperty("server.port");
            prop.getProperty("hibernate.connection.username");
            prop.getProperty("hibernate.connection.password");
            prop.getProperty("hibernate.connection.url");
            return prop;
        }catch (Exception e){
            return createConfig();
        }
    }

    public static Properties createConfig() throws IOException {
        OutputStream output = new FileOutputStream("conf.properties");
        Properties prop = new Properties();
        prop.setProperty("server.port", "7170");
        prop.setProperty("hibernate.connection.username", "root");
        prop.setProperty("hibernate.connection.password", "");
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3307/ecommerceQualidade?serverTimezone=UTC&ampcreateDatabaseIfNotExist=true");
        prop.store(output, null);
        return prop;
    }
}