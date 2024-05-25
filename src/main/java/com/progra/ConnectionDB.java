package com.progra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    // Datos de inicio de sesion de la base de datos Oracle
    private static final String user = "talonario";
    private static final String password = "1234";
    // URL de conexion a la base de datos Oracle
    private static String url = "jdbc:oracle:thin:@localhost:1521:XE";

    // Metodo para obtener la conexion
    public static Connection getConnectionOracle() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}

