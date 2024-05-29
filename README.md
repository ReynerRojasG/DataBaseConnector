# Proyecto de Mapeo de Clases a Base de Datos Oracle

Este proyecto permite mapear clases Java a una base de datos Oracle. Para ello, se requiere tener instalado el driver ojdbc en su versión 11 y agregar la dependencia correspondiente en el archivo `pom.xml` del proyecto.

## Instalación

1. **Agregar la dependencia de Oracle JDBC Driver en el archivo `pom.xml`**:

    ```xml
    <!-- Dependencia de Oracle JDBC Driver (cambiar artifactID en caso de tener otro) -->   
    <dependencies>
        <dependency>
            <groupId>com.oracle.database.jdbc</groupId>
            <artifactId>ojdbc11</artifactId>
            <version>21.3.0.0</version>
        </dependency>
    </dependencies>
    ```

2. **Crear objetos en el `main`**:

    Después de lograr una conexión exitosa, crear objetos correspondientes a las dos clases por defecto que vienen junto con el código (`Trabajador`, `Persona`).

3. **Mapear los datos del objeto a una tabla en la base de datos**:

    Llamar al método `mapToDatabase(Object)` para agregar los datos del objeto generado a una tabla en la base de datos.

4. **Ver los datos de la tabla en la consola**:

    Para ver los datos de la tabla directamente en la consola, llamar al método `readTable(Object)`, el cual obtiene todos los datos de la tabla y los imprime.

5. **Actualizar un objeto en la tabla**:

    Para actualizar un objeto en la tabla, primero proporcionar los nuevos datos con los métodos `set` de cada clase, por ejemplo:
    ```java
    Juan.setNombre("Juan mejorado");
    Juan.setEdad(33);
    ```
    Posteriormente, actualizar el objeto en la tabla con el método `updateTable(Object)`. Para ver los cambios actualizados, llamar nuevamente a `readTable(Object)`.

6. **Eliminar un objeto de la tabla**:

    Para eliminar un objeto de la tabla en la base de datos, llamar al método `deleteFromTable(Object)`, el cual elimina el objeto identificándolo por su ID.
