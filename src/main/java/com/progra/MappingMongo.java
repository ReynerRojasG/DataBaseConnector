package com.progra;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;

public class MappingMongo {
    private MongoDatabase connection_mongo;
    // Constructor para inicializar la conexion a la base de datos MongoDB
    public MappingMongo(MongoDatabase connection_mongo) {
        this.connection_mongo = connection_mongo;
    }
    // Método para insertar un objeto en la colección correspondiente en MongoDB
    public void insertToMongo(Object object){
        try {
            // Obtiene la clase del objeto
            Class<?> classMap = object.getClass();
            // Obtiene el nombre de la coleccion en mayuscula
            String collectionName = classMap.getSimpleName().toUpperCase();
            // Obtiene la coleccion de MongoDB
            MongoCollection<Document> collection = connection_mongo.getCollection(collectionName);
            // Convertir el objeto a un documento de MongoDB (Json)
            Document document = convertToJson(object, classMap); 
            Object idValue = document.get("_id");
            // Verifica si el objeto ya existe en la coleccion
            if(idValue != null){
                if (selectByID(classMap, idValue) != null) {
                    Field nameField = classMap.getDeclaredField("nombre");
                    nameField.setAccessible(true);
                    String name = (String) nameField.get(object);
                    System.out.println("El elemento " + name + " no se pudo agregar, ya existe un elemento con el mismo ID en la coleccion");
                    return;
                }
            }
            // Inserta el documento en la coleccion
            collection.insertOne(document);
            System.out.println("Se agrego un elemento a la coleccion");
        } catch (Exception e) {
            System.out.println("Se produjo un error al intentar insertar un elemento");
        }
    }   
     // Metodo para convertir un objeto en un documento de MongoDB
    private Document convertToJson(Object object, Class<?> classMap) throws IllegalAccessException{
        Document document = new Document();
        Field[] elements = classMap.getDeclaredFields();
        String IdField = "id";

        for(Field element:elements){
            element.setAccessible(true);
            String elementName = element.getName();
            Object value = element.get(object);
            // Si el campo que se esta insertando es el id
            if(elementName.equals(IdField)){
                document.append("_id",value);
            } else{
                document.append(elementName, value);
            }
        }
        return document;
    }

// Deserialización
    private <T> T instance(Class<T> classMap, Document document ) {
       T instance = null;
       try {
        Constructor<T> constructor = classMap.getDeclaredConstructor();
        constructor.setAccessible(true);
        instance = constructor.newInstance();
       } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
       System.out.println("Error al instanciar el constructor");
        e.printStackTrace();
       }
        
        Field[] elements = classMap.getDeclaredFields();
        String IdField = "id";

        for(Field element:elements){
            try {
                element.setAccessible(true);
                String elementName = element.getName();
                Object value = document.get(elementName);
    
                if(value == null && elementName.equals(IdField)){
                    value = document.get("_id");
                }
                element.set(instance, value);  
            } catch (IllegalAccessException e) {
                System.out.println("error al instanciar");
                e.printStackTrace();
            }
            
        }
        return instance;
    }
    // Metodo para seleccionar todos los documentos de una coleccion y convertirlos en una lista de objetos
    public <T> List<T> readCollection(Class<T> classMap){
        List<T> result = new ArrayList<>();
        String collectionName = classMap.getSimpleName().toUpperCase();
        MongoCollection<Document> collection = connection_mongo.getCollection(collectionName);
        
        try(MongoCursor<Document> cursor = collection.find().iterator()) {
            while(cursor.hasNext()){
               Document document = cursor.next();
               T instance = instance(classMap, document); 
               result.add(instance);
            }
            
        } catch (Exception e) {
            System.out.println("error al seleccionar el elemento");
        }
        return result;
    }
    // Metodo para seleccionar un documento por su ID y convertirlo en un objeto
    public <T> T selectByID(Class<T> classMap, Object id){
        String collectionName = classMap.getSimpleName().toUpperCase();
        MongoCollection<Document> collection = connection_mongo.getCollection(collectionName);
        Document query = new Document("_id", id);

        try{
           Document document = collection.find(query).first();
           if(document != null){
            return instance(classMap, document);
           }
            
        } catch (Exception e) {
            System.out.println("error al seleccionar el elemento");
            e.printStackTrace();
        }
        return null;
    }
    // Metodo para actualizar un documento en la coleccion
    public void updateCollection(Object object){
           
            Class<?> classMap = object.getClass();
            String collectionName = classMap.getSimpleName().toUpperCase();
            try {
            MongoCollection<Document> collection = connection_mongo.getCollection(collectionName);

            Document document = convertToJson(object, classMap);
            
            Field idField = classMap.getDeclaredField("id");
            idField.setAccessible(true);
            Object idValue = idField.get(object);


            if(idValue != null){
               collection.replaceOne(Filters.eq("_id", idValue), document);
                
               System.out.println("Se actualizo el elemento en la coleccion " + collectionName + " con id " + idValue);
            } else{
               System.out.println("El id proporcionado no es valido");
            } 

        } catch (Exception e) {
            System.out.println("Error al actualizar la coleccion");
        }
    }
    // Metodo para eliminar un documento de la coleccion utilizando el ID del objeto
    public void deleteFromCollection(Object object){
        try{
        Class<?> classMap = object.getClass();
        String collectionName = classMap.getSimpleName().toUpperCase();
        MongoCollection<Document> collection = connection_mongo.getCollection(collectionName);

        Field idField = classMap.getDeclaredField("id");
        idField.setAccessible(true);
        Object idValue = idField.get(object);
        Document query = new Document("_id", idValue);
        
         collection.deleteOne(query);
         System.out.println("Se elimino el elemento de la coleccion " + collectionName);
        } catch(Exception e){
            System.out.println("Error al eliminar un elemento de la coleccion");
        }
    }

}
