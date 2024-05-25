package com.progra;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        
        try(Connection conexionOracle = ConnectionDB.getConnectionOracle()){
        // Informacion si se logra conectar a la base de datos
        System.out.println("Conexion exitosa a la base de datos\n"); 
        // Crea un mapeo
        MappingOracle mapOracle = new MappingOracle(conexionOracle);
        // Crea un objeto persona
        Persona Juan = new Persona(1, "Juan", 15);
        // Crea un objeto trabajador
        Trabajador Remus = new Trabajador(1,"Remus Lupin",38,"Profesor");

        mapOracle.mapToDatabase(Juan);
        mapOracle.readTable(Juan);
    /*  Descomentar para crear los objetos en la tabla
        mapOracle.mapToDatabase(Juan);
        mapOracle.mapToDatabase(Remus);
    */

    /*  Descomentar para leer los elementos en la tabla
        mapOracle.readTable(Juan);
        mapOracle.readTable(Remus);
    */

    /* Descomentar para actualizar los elementos de la tabla
        Juan.setNombre("Juan mejorado");
        Juan.setEdad(33);
        mapOracle.updateTable(Juan);
        mapOracle.readTable(Juan);
   */
       
    /* Descomentar para eliminar un objeto de la tabla
        mapOracle.deleteFromTable(Juan);
    */   

        } catch(SQLException e){
            System.out.println("Error al iniciar la conexion a Oracle");
            e.printStackTrace();
        }

    }
}