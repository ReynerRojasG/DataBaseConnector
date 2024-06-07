package com.progra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ConnectionString;


public class ConnectionDB {
    // DATOS PARA ORACLE
    private static final String user = "talonario";
    private static final String password = "1234";
    // URL de conexion a la base de datos Oracle
    private static String url = "jdbc:oracle:thin:@localhost:1521:XE";

    // DATOS PARA MONGODB
    private static String mongoUrl = "mongodb://localhost:27017";
    private static String mongoDbName = "mapmongo_db";

    // DATOS PARA MYSQL
    private static String mySql_URL = "jdbc:mysql://localhost:3306/my_connector";
    private static String mySql_USER = "root";
    private static String mySql_PASSWORD = "1234";

    // Metodo para obtener la conexion de Oracle
    public static Connection getConnectionOracle() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    // Metodo para obtener la conexion de MongoDB
    public static MongoDatabase getConnectionMongo(){
        ConnectionString connectionString = new ConnectionString(mongoUrl);
        MongoClient mongoClient = MongoClients.create(connectionString);
        return mongoClient.getDatabase(mongoDbName);
    }
    // Metodo para obtener la conexion de mySQL
    public static Connection getConnectionMySQL() throws SQLException {
        return DriverManager.getConnection(mySql_URL, mySql_USER, mySql_PASSWORD);
    }
}

