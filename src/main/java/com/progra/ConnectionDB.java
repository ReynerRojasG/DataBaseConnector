package com.progra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ConnectionString;


public class ConnectionDB {
    // Datos de inicio de sesion de la base de datos Oracle
    private static final String user = "talonario";
    private static final String password = "1234";
    // URL de conexion a la base de datos Oracle
    private static String url = "jdbc:oracle:thin:@localhost:1521:XE";


    private static String mongoUrl = "mongodb://localhost:27017";
    private static String mongoDbName = "mapmongo_db";

    // Metodo para obtener la conexion
    public static Connection getConnectionOracle() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public static MongoDatabase getConnectionMongo(){
        ConnectionString connectionString = new ConnectionString(mongoUrl);
        MongoClient mongoClient = MongoClients.create(connectionString);
        return mongoClient.getDatabase(mongoDbName);
    }
}

