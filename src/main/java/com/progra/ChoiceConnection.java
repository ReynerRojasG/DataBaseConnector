package com.progra;

import java.util.List;
import java.sql.SQLException;
import java.sql.Connection;
import com.mongodb.MongoException;
import com.mongodb.client.MongoDatabase;

public class ChoiceConnection {
    public void oracleDB(){
     /*        
        try(Connection conexionOracle = ConnectionDB.getConnectionOracle()){
        // Informacion si se logra conectar a la base de datos
        System.out.println("Conexion exitosa a la base de datos ORACLE\n"); 
        // Crea un mapeo
        MappingOracle mapOracle = new MappingOracle(conexionOracle);
        // Crea un objeto persona
        Persona Juan = new Persona(1, "Juan", 15);
        // Crea un objeto trabajador
        Trabajador Remus = new Trabajador(1,"Remus Lupin",38,"Profesor");

      
    /*  Descomentar para crear los objetos en la tabla
        // CASO ORACLE
        mapOracle.mapToDatabase(Juan);
        mapOracle.mapToDatabase(Remus);
    */

    /*  Descomentar para leer los elementos en la tabla
        // CASO ORACLE
        mapOracle.readTable(Juan);
        mapOracle.readTable(Remus);
    */

    /* Descomentar para actualizar los elementos de la tabla
        // CASO ORACLE
        Juan.setNombre("Juan mejorado");
        Juan.setEdad(33);
        mapOracle.updateTable(Juan);
        mapOracle.readTable(Juan);
   */
       
    /* Descomentar para eliminar un objeto de la tabla
         // CASO ORACLE
        mapOracle.deleteFromTable(Juan);
        mapOracle.deleteFromTable(Remus);
    */   

    /* 
        } catch(SQLException e){
            System.out.println("Error al iniciar la conexion a Oracle");
            e.printStackTrace();
        }
    */
    }

    public void mongoDB(){
      /* 
    try{
        MongoDatabase mongoConnection = ConnectionDB.getConnectionMongo();
        MappingMongo mapMongo = new MappingMongo(mongoConnection);
        mongoConnection.listCollectionNames().first();
        Persona Juan = new Persona(1, "Juancin", 24);
        Trabajador Juanito = new Trabajador(2,"Juan juaan",38,"Profesor");
        System.out.println("Conectado a la base de datos MongoDB");

        
       /* Descomentar para insertar los objetos
        mapMongo.insertToMongo(Juan);
        mapMongo.insertToMongo(Juanito);
        */

        
       /* Descomentar para leer los elementos en la coleccion
       List<Persona> personas = mapMongo.readCollection(Persona.class);
       List<Trabajador> trabajador = mapMongo.readCollection(Trabajador.class);

         System.out.println("\n");
       for(Persona persona:personas){
        System.out.println(persona);
       }
         System.out.println("\n");
       for(Trabajador trabajador:trabajador){
        System.out.println(trabajador);
       }
       */ 
      
       /* Descomentar para actualizar la coleccion 
       mapMongo.updateCollection(Juan);
       mapMongo.updateCollection(Juanito);
    
       List<Persona> personaActualizada = mapMongo.readCollection(Persona.class);
       List<Trabajador> trabajadorActualizado = mapMongo.readCollection(Trabajador.class);

         System.out.println("\n");
       for(Persona persona:personaActualizada){
        System.out.println(persona);
       }
         System.out.println("\n");
       for(Trabajador trabajador:trabajadorActualizado){
        System.out.println(trabajador);
       }
      */

      /* Descomentar para eliminar un objeto de la coleccion
        mapMongo.deleteFromCollection(Juanito);
      */
      /* 
    } catch(MongoException e){
        System.out.println("error al intentar conectarse a MongoDB");
    }
      */
    }

    public void mySQL_DB(){
      /*     
        try(Connection connectionMySQL = ConnectionDB.getConnectionMySQL()){
        
        // Informacion si se logra conectar a la base de datos
        System.out.println("Conexion exitosa a la base de datos MySQL\n"); 
        // Crea un mapeo


        MappingOracle mapMySQL = new MappingOracle(connectionMySQL);
        // Crea un objeto persona
        Persona Juan = new Persona(1, "Juan", 15);
        // Crea un objeto trabajador
        Trabajador Remus = new Trabajador(1,"Remus Lupin",38,"Profesor");

      
    /*  Descomentar para crear los objetos en la tabla
        mapMySQL.mapToDatabase(Juan);
        mapMySQL.mapToDatabase(Remus);

    */

    /*  Descomentar para leer los elementos en la tabla
        mapMySQL.readTable(Juan);
        mapMySQL.readTable(Remus);
    */

    /* Descomentar para actualizar los elementos de la tabla
        Juan.setNombre("Juan mejorado");
        Juan.setEdad(33);
        mapMySQL.updateTable(Juan);
        mapMySQL.readTable(Juan);
   */
       
    /* Descomentar para eliminar un objeto de la tabla
        mapMySQL.deleteFromTable(Juan);
        mapMySQL.deleteFromTable(Remus);
    */   

     /* 
        } catch(SQLException e){
            System.out.println("Error al iniciar la conexion a MySQL");
            e.printStackTrace();
        }
    */
    }
}
