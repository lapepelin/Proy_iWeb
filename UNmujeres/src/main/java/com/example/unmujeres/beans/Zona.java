package com.example.onu.beans;

public class Zona {
    private int idzona;
    private String nombre;

    //-------------------------------------------------
    public Zona(int idzona, String nombre) {
        this.idzona = idzona;
        this.nombre = nombre;
    }
    //-------------------------------------------------

    public int getIdzona() {
        return idzona;
    }

    public void setIdzona(int idzona) {
        this.idzona = idzona;
    }
    //-------------------------------------------------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

