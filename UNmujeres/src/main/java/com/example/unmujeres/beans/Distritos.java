package com.example.onu.beans;

public class Distritos {
    private int iddistritos;
    private String nombre;
    private int zona_idzona;
    //-------------------------------------------------
    public Distritos() {
    }
    //-------------------------------------------------

    public Distritos(int iddistritos, String nombre, int zona_idzona) {
        this.iddistritos = iddistritos;
        this.nombre = nombre;
        this.zona_idzona = zona_idzona;
    }
    //-------------------------------------------------
    public int getIddistritos() {
        return iddistritos;
    }

    public void setIddistritos(int iddistritos) {
        this.iddistritos = iddistritos;
    }
    //-------------------------------------------------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //-------------------------------------------------
    public int getZona_idzona() {
        return zona_idzona;
    }

    public void setZona_idzona(int zona_idzona) {
        this.zona_idzona = zona_idzona;
    }
}

