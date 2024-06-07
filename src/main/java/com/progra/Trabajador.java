package com.progra;
// Clases por defecto para pruebas
public class Trabajador {
    private int id;

    public int getId() {
        return id;
    }

   public void setId(int id) {
        this.id = id;
    }

    private String puesto;

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    private int edad;

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    public Trabajador(){}

    public Trabajador(int id,String nombre, int edad, String puesto) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.puesto = puesto;
    }

    @Override
    public String toString() {
        return "Trabajador [ id = " + id + " | puesto = " + puesto + " | nombre = " + nombre + " | edad = " + edad + " ]";
    }

    
}
