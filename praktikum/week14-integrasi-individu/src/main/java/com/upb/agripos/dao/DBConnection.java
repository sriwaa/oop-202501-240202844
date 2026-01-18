package com.upb.agripos.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static Connection instance;

    private DBConnection() {}

    public static Connection getInstance() {
        if (instance == null) {
            try {
                instance = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/agripos",
                    "postgres",
                    "225522"
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }
}
