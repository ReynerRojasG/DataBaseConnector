package com.progra;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.sql.ResultSetMetaData;

public class MappingOracle{ 

    private Connection connect;
    private Set<String> tableExist;
    
    // Constructor
    public MappingOracle(Connection p_connect) {    
            this.connect = p_connect;
            // Inicializa un conjunto para almacenar los nombres de las tablas existentes.
            this.tableExist = new HashSet<>();
    }

   // Mapeao a Oracle
   public void mapToDatabase(Object object) {
        try {
            Class<?> classMap = object.getClass();
            String tableName = classMap.getSimpleName().toUpperCase();

            // Crea la tabla si no existe 
            createNewTable(tableName, classMap);
            // Inserta los datos 
            insertData(tableName, classMap, object);
           
        } catch (SQLException | IllegalAccessException e) {
            System.out.println("Error al mapear la clase a la tabla: " + e.getMessage());
        } 
    } 

  // Comprueba si una tabla existe en la base de datos 
  private boolean toExistingTable(String tableName) {
    // Comprueba si la tabla ya esta en el conjunto de tablas existentes
    if (tableExist.contains(tableName)) {
        return true;
    }

    String query = "";
    try {
        // Consulta SQL para verificar la existencia de la tabla en la base de datos
        String dbProductName = connect.getMetaData().getDatabaseProductName().toLowerCase();
        
        if (dbProductName.contains("oracle")) {
            // Query para Oracle
            query = "SELECT count(*) FROM user_tables WHERE table_name = ?";
        } else if (dbProductName.contains("mysql")) {
            // Query para mySQL
            query = "SELECT count(*) FROM information_schema.tables WHERE table_schema = DATABASE() AND table_name = ?";
        } else {
            throw new SQLException("Base de datos no soportada: " + dbProductName);
        }

        // Consulta SQL para verificar la existencia de la tabla en la base de datos
        try (PreparedStatement statement = connect.prepareStatement(query)) {
            statement.setString(1, tableName.toUpperCase()); // Asegura que el nombre de la tabla esté en mayúsculas
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    // Si el recuento es mayor que cero, la tabla existe
                    if (count > 0) {
                        // Agrega la tabla al conjunto de tablas existentes
                        tableExist.add(tableName);
                        return true;
                    }
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al verificar si existe la tabla: " + e.getMessage());
    }

    // Si no se encuentra la tabla, devuelve false
    return false;
}

   // Metodo para crear una nueva tabla en la base de datos
   private void createNewTable(String tableName, Class<?> classMap) throws SQLException
   {    // Verifica si la tabla ya existe en la base de datos
        if (!toExistingTable(tableName)) {
            // Construye la consulta SQL para crear la tabla
           StringBuilder query = new StringBuilder("CREATE TABLE ").append(tableName).append(" (");
            // Obtiene los campos del objeto
           Field[] elements = classMap.getDeclaredFields();

           // Agrega columnas basadas en los campos del objeto
           for(Field element : elements)
           {
               element.setAccessible(true);
               String elementName = element.getName();
               String elementType = dataTypes(element.getType());
               // Agrega el nombre y tipo de la columna a la consulta
               query.append(elementName).append(" ").append(elementType);
               if(elementName.equals("id"))
               {
                   query.append(" PRIMARY KEY");
               }
               query.append(", ");
           }
           // Elimina la coma y el espacio adicionales al final de la consulta
           query.delete(query.length() - 2, query.length());
           query.append(")");
           
           try(PreparedStatement statement = connect.prepareStatement(query.toString()))
           {    
               // Ejecuta la consulta para asi crear la tabla
               statement.executeUpdate();
               System.out.println("Tabla " + tableName + " Se ha creado correctamente.");
               System.out.println(" ");
           } catch (SQLException e) {
               System.out.println("Error al crear la nueva tabla: " + e.getMessage());
               throw e; 
           }
        }  
    }

   // Determina el tipo de datos SQL correspondiente al tipo de datos Java
   private String dataTypes(Class<?> type) {
        if (type == String.class) {
            return "VARCHAR(255)";
        } 
        if (type == int.class || type == Integer.class) {
            return "INT";
        }
        if (type == double.class || type == Double.class) {
            return "DOUBLE";
        }  
        if (type == float.class || type == Float.class) {
            return "FLOAT";
        }  
        if (type == boolean.class || type == Boolean.class) {
            return "BOOLEAN";
        }  
        return "VARCHAR(255)"; // Por defecto, se considera como String
    } 

   // Obtiene el nombre del objeto, para luego poder identificarlo en otras funciones
   private String getObjectName(String tableName, Class<?> classMap, Object object) throws IllegalAccessException {  
        Field nameField = null;
        try {
            // Intenta obtener el campo 'nombre' de la clase que representa el mapeo del objeto
            nameField = classMap.getDeclaredField("nombre");
            nameField.setAccessible(true);
            // Devuelve el valor del campo 'nombre' del objeto
            return (String) nameField.get(object);
        } catch (NoSuchFieldException e) {
            System.out.println("No se encontro el campo 'nombre' en la clase " + classMap.getSimpleName());
            return "desconocido";
        }
    }

   // Comprueba si un objeto existe antes de insertarlo a la base de datos
   private boolean objectExists(String tableName, Class<?> classMap, Object object) throws IllegalAccessException {
    // Construye la consulta SQL para contar los registros que coinciden con los valores del objeto
    StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM ").append(tableName).append(" WHERE ");

    // Lista para almacenar los valores unicos de los campos del objeto
    List<Object> uniqueValues = new ArrayList<>();

    // Obtener los campos del objeto 
    Field[] elements = classMap.getDeclaredFields();
    for (Field element : elements) {
        element.setAccessible(true);
        String elementName = element.getName();
        Object value = element.get(object);

        // Si el valor del campo no es nulo, agregarlo a la consulta 
        if (value != null) {
            query.append(elementName).append(" = ? AND ");
            uniqueValues.add(value);
        }
    }

    // Eliminar el exceso de caracteres de la consulta
    query.delete(query.length() - 5, query.length());

    // Ejecutar la consulta para verificar si el objeto ya existe
        try (PreparedStatement checkStatement = connect.prepareStatement(query.toString())) {
           int index = 1;
            // Itera sobre los valores unicos y asignarlos a la consulta preparada
             for (Object value : uniqueValues) {
                  checkStatement.setObject(index++, value);
                 }
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                         if (resultSet.next()) {
                         int count = resultSet.getInt(1);
                             if(count > 0) {
                                // Obtiene el nombre del objeto, para imprimir un mensaje informativo
                                 String objectName = getObjectName(tableName, classMap, object);
                             
                                 System.out.println("El objeto " + objectName + " ya existe en la tabla " + tableName + ".");
                                 System.out.println(" ");
                             }
                            return count > 0; // Devolver true si el objeto ya existe
                         }
                    }
       } catch (SQLException e) {
           System.out.println("Error al verificar la existencia de datos: " + e.getMessage());
       }
         return false; // Devolver false si hay un error o el objeto no existe
   }

   // Inserta los datos en la base de datos 
   private void insertData(String tableName, Class<?> classMap, Object object) throws IllegalAccessException {
    // Verificar si el objeto ya existe en la tabla antes de insertarlo
    if (!objectExists(tableName, classMap, object)) {
       // Prepara la consulta SQL para insertar
       StringBuilder query = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
       StringBuilder values = new StringBuilder("VALUES (");
   
       // Obtener los campos del objeto
       Field[] elements = classMap.getDeclaredFields();
       for (Field element : elements) {
           element.setAccessible(true);
           // Obtener el nombre del campo
           String elementName = element.getName();
           // Obtener el valor del campo del objeto
           Object value = element.get(object);
   
           // Si el valor del campo no es nulo, agregarlo a la consulta
           if (value != null) {
               query.append(elementName).append(", ");
               values.append("?, ");
           }
       }
   
       // Eliminar la coma y el espacio extra al final de las listas de campos y valores
       query.delete(query.length() - 2, query.length()).append(")");
       values.delete(values.length() - 2, values.length()).append(")");
   
       // Combinar las partes de la consulta SQL
       String insertQuery = query.toString() + " " + values.toString();
   
       try (PreparedStatement statement = connect.prepareStatement(insertQuery)) {
           int index = 1;
           for (Field element : elements) {
               element.setAccessible(true);
               // Obtener el valor del campo del objeto
               Object value = element.get(object);
              // Si el valor del campo no es nulo, establecer el valor en la consulta preparada
               if (value != null) {
                   statement.setObject(index++, value);
               }
           }
   
           // Ejecuta la consulta 
           statement.executeUpdate();
           System.out.println("Datos insertados en la tabla " + tableName + " correctamente.");
           System.out.println(" ");
       } catch (SQLException e) {
           System.out.println("Error al insertar datos en la tabla: " + e.getMessage());
       }
    }
}

   // Lee los datos
   public void readTable(Object object) {
   // Obtiene el nombre de la tabla basado en el nombre de la clase del objeto
   String tableName = object.getClass().getSimpleName().toUpperCase();

        // Verifica si la tabla existe en la base de datos
        if (toExistingTable(tableName)){
                // Construye la consulta SQL para seleccionar todos los datos de la tabla
                StringBuilder query = new StringBuilder("SELECT * FROM ").append(tableName);
                    try(PreparedStatement statement = connect.prepareStatement(query.toString())) {
                        // Ejecuta la consulta y obtiene el resultado
                        ResultSet resultSet = statement.executeQuery();
                        // Obtiene los metadatos del resultado
                        ResultSetMetaData metaData = resultSet.getMetaData();   
                        // Obtiene el numero de columnas en el resultado
                        int columns = metaData.getColumnCount();
                        
                        System.out.println("---DATOS DE LA TABLA " + tableName + "---");
                        // Itera sobre cada fila del resultado
                        while (resultSet.next()) {
                        // Itera sobre cada columna de la fila
                            for (int i = 1; i <= columns; i++) {
                                String columnName = metaData.getColumnName(i);
                                String columnValue = resultSet.getString(i);
                                // Imprime el nombre y el valor de la columna
                                System.out.print(columnName + ": " + columnValue + " | ");
                            }
                        }
                    } catch(SQLException e){
                        System.out.println("Error al recuperar los datos de la tabla: " + e.getMessage());
                    }
                        System.out.println("\n"); 
            }
    }
   
   // Elimina el objeto por su ID
   public void deleteFromTable(Object object) {
        try {
            // Obtiene la clase del objeto y el nombre de la tabla
            Class<?> classMap = object.getClass();
            String tableName = classMap.getSimpleName().toUpperCase();

            // Verifica si la tabla existe en la base de datos
            if(toExistingTable(tableName)){
                // Obtiene el campo de ID del objeto
                Field idField = classMap.getDeclaredField("id");
                idField.setAccessible(true);
                // Obtiene el valor del ID del objeto
                int id = idField.getInt(object);

                // Construye la consulta SQL para eliminar el registro por ID
                String query = "DELETE FROM " + tableName + " WHERE id = ?";

                // Ejecuta la consulta preparada para eliminar el registro
                try(PreparedStatement statement = connect.prepareStatement(query)){
                        statement.setInt(1, id);
                        int rowsAffected = statement.executeUpdate();

                        // Verifica si se elimino correctamente el registro
                        if (rowsAffected > 0) {
                            System.out.println("Objeto eliminado de la tabla " + tableName + " correctamente.");
                        } else {
                            System.out.println("No se encontro ningun registro con el ID proporcionado en la tabla " + tableName + ".");
                        }

                }
            } else {
                System.out.println("La tabla " + tableName + " no existe en la base de datos.");
            }
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            System.out.println("Error al eliminar el objeto de la tabla: " + e.getMessage());
        }
   }

   // Actualiza la tabla
   public void updateTable(Object object) {
        try {
             // Obtiene la clase del objeto y el nombre de la tabla
            Class<?> classMap = object.getClass();
            String tableName = classMap.getSimpleName().toUpperCase();
            // Verifica si la tabla existe en la base de datos
            if(toExistingTable(tableName)){
                // Obtiene lo relacionado con los ID
                Field idField = classMap.getDeclaredField("id");
                idField.setAccessible(true);
                int id = idField.getInt(object);
                 // Construye la consulta SQL para actualizar el registro
                StringBuilder query = new StringBuilder("UPDATE ").append(tableName).append(" SET ");

                Field[] elements = classMap.getDeclaredFields();
                for(Field element : elements){
                    // Ignora el campo de ID en la actualizacion, por ser la primary key (no modificable)
                    if(!element.getName().equals("id")){
                        element.setAccessible(true);
                        query.append(element.getName()).append(" = ?, ");
                    }
                }
                query.delete(query.length() - 2, query.length()); 
                query.append(" WHERE id = ?");

                // Ejecuta la consulta preparada para actualizar el registro
                String inserQuery = query.toString(); 
                try(PreparedStatement statement = connect.prepareStatement(inserQuery)){
                    int index = 1;
                    for(Field element : elements){
                        // Ignora el campo de ID 
                        if(!element.getName().equals("id")){
                            element.setAccessible(true);
                             // Obtiene el valor del campo del objeto
                            Object value = element.get(object);
                            // Asigna el valor del campo a la consulta preparada en el indice correspondiente
                            statement.setObject(index++, value);
                        }
                    }
                    // Establece el valor que tiene ID en la consulta preparada 
                    statement.setInt(index, id);
                    // Ejecuta la consulta para actualizar el registro
                    int rowsAffected = statement.executeUpdate();

                        // Verifica si se actualizó correctamente el registro
                        if (rowsAffected > 0) {
                            System.out.println("Objeto actualizado de la tabla " + tableName + " correctamente.");
                        } else {
                            System.out.println("No se encontro ningun registro con el ID proporcionado en la tabla " + tableName + ".");
                        }
                }
                
            } else {
                System.out.println("La tabla " + tableName + " no existe");
            } 

        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            System.out.println("Error al  actualizar la tabla.");
        }
   }

}