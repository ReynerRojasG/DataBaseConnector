# Proyecto de Mapeo de Clases a Base de Datos

Este proyecto permite mapear clases Java a bases de datos Oracle y MongoDB. A continuación se detalla la configuración y el uso para cada base de datos.

## Parte de Oracle

Para poder mapear una clase a una base de datos de Oracle en este proyecto, primero se necesitará agregar la dependencia en el `pom.xml` del proyecto. El cual viene dado por:

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

1. Para poder generar una conexión con la base de datos, primero necesitamos dirigirnos a `ChoiceConnection.java`, para luego descomentar el try-catch el cual permite obtener una conexión gracias a la clase `ConnectionDB`, el cual posee una URL, user y un password y lo más importante un método `getConnectionOracle()`.
![conexion oracle](https://github.com/ReynerRojasG/DataBaseConnector/assets/142065130/785b3f5b-815f-4632-9346-7ae150e02538)

2. Luego de lograr una conexión exitosa, lo que se necesitará es crear objetos correspondientes a las dos clases por defecto que vienen junto con el código (`Trabajador`, `Persona`).

3. Necesitaremos llamar al método `mapToDatabase(Object)` para poder así agregar los datos del objeto generado en una tabla en la base de datos.

4. Para ver los datos de la tabla directamente en nuestra consola, podremos llamar al método `readTable(Object)`, el cual logra obtener todos los datos de la tabla y traerlos para poder imprimirlos.

5. En caso de querer actualizar el objeto en la tabla, necesitaremos primero brindar nuevos datos con los métodos `set` de cada clase, por ejemplo:
    ```java
    Juan.setNombre("Juan mejorado");
    Juan.setEdad(33);
    ```
    Y así posteriormente actualizar el objeto en la tabla con el método `updateTable(Object)`. Si es de nuestro gusto ver los cambios actualizados podríamos llamar a `readTable(Object)` de vuelta.

6. Si quisiéramos eliminar el objeto de la tabla en la base de datos llamaríamos a `deleteFromTable(Object)`, el cual permite eliminar el objeto identificándolo por su ID.

## Parte de MongoDB

Para poder mapear una clase a una base de datos de MongoDB en este proyecto, se necesitará agregar la dependencia en el `pom.xml` del proyecto también. El cual viene dado por:

```xml
<!-- Dependencia MongoDB -->
<dependencies>
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>4.11.1</version>
    </dependency>
</dependencies>
```

1. Para poder generar una conexión con la base de datos, primero necesitamos dirigirnos a `ChoiceConnection.java`, para luego descomentar el try-catch el cual también funciona con la clase `ConnectionDB` y posee `getConnectionMongo()`.
![conexion mongo](https://github.com/ReynerRojasG/DataBaseConnector/assets/142065130/7c0e17d2-1945-44ae-afb0-b76e71484e4f)

2. Luego de lograr una conexión exitosa, lo que se necesitará es crear objetos correspondientes a las dos clases por defecto que vienen junto con el código (`Trabajador`, `Persona`).

3. Necesitaremos llamar al método `insertToMongo(Object)` para poder así agregar los datos del objeto generado en una tabla en la base de datos.

4. Para ver los datos de la tabla directamente en nuestra consola, crear una lista de Personas o Trabajadores e igualarla al método `readCollection(Object)`, el cual logra obtener todos los datos de la colección y traerlos para poder imprimirlos con un `for`.

5. En caso de querer actualizar el objeto en la colección, necesitaremos primero brindar nuevos datos con los métodos `set` de cada clase, por ejemplo:
    ```java
    Juan.setNombre("Juan mejorado");
    Juan.setEdad(33);
    ```
    Y así posteriormente actualizar el objeto en la tabla con el método `updateCollection(Object)`. Si es de nuestro gusto ver los cambios actualizados podríamos llamar a `readCollection(Object)` de vuelta, generando primero una lista de Personas o Trabajadores y recorrer el `for`.

6. Si quisiéramos eliminar el objeto de la tabla en la base de datos llamaríamos a `deleteFromCollection(Object)`, el cual permite eliminar el objeto identificándolo por su ID.

 ## Parte de Oracle
Para poder mapear una clase a una base de datos de Oracle en este proyecto, primero se necesitará agregar la dependencia en el `pom.xml` del proyecto. El cual viene dado por:   

```xml
<!--  Dependencia MongoDB  -->
     <dependency>
      <groupId>org.mongodb</groupId>
      <artifactId>mongodb-driver-sync</artifactId>
      <version>4.11.1</version>
     </dependency>
```

1. Para poder generar una conexión con la base de datos, primero necesitamos dirigirnos a `ChoiceConnection.java`, para luego descomentar el try-catch el cual permite obtener una conexión gracias a la clase `ConnectionDB`, el cual posee una URL, user y un password y lo más importante un método `getConnectionMySQL()`, cabe aclarar que funciona con los mismos métodos de la clase de Oracle, por lo que:
![conexion mySQL](https://github.com/ReynerRojasG/DataBaseConnector/assets/142065130/6aeae1ad-f16b-4499-8b32-1366edbd4a44)

2. Luego de lograr una conexión exitosa, lo que se necesitará es crear objetos correspondientes a las dos clases por defecto que vienen junto con el código (`Trabajador`, `Persona`).

3. Necesitaremos llamar al método `mapToDatabase(Object)` para poder así agregar los datos del objeto generado en una tabla en la base de datos.

4. Para ver los datos de la tabla directamente en nuestra consola, podremos llamar al método `readTable(Object)`, el cual logra obtener todos los datos de la tabla y traerlos para poder imprimirlos.

5. En caso de querer actualizar el objeto en la tabla, necesitaremos primero brindar nuevos datos con los métodos `set` de cada clase, por ejemplo:
    ```java
    Juan.setNombre("Juan mejorado");
    Juan.setEdad(33);
    ```
    Y así posteriormente actualizar el objeto en la tabla con el método `updateTable(Object)`. Si es de nuestro gusto ver los cambios actualizados podríamos llamar a `readTable(Object)` de vuelta.

6. Si quisiéramos eliminar el objeto de la tabla en la base de datos llamaríamos a `deleteFromTable(Object)`, el cual permite eliminar el objeto identificándolo por su ID.

   

