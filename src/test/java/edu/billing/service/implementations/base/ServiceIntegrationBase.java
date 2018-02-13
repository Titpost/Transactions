package edu.billing.service.implementations.base;

import org.junit.AfterClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;


public class ServiceIntegrationBase {

    @Autowired
    protected EmbeddedDatabase db;

    protected static EmbeddedDatabase dataBase;

    /**
     * Closes DataBase connection
     */
    @AfterClass
    public static void tearDown() {
        dataBase.shutdown();
        dataBase = null;
    }
}
