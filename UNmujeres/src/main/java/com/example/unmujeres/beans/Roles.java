package com.example.onu.beans;

public class Roles {
    private int idroles;
    private String nombre;

    //-------------------------------------------------
    public Roles(int idroles, String nombre) {
        this.idroles = idroles;
        this.nombre = nombre;
    }

    //-------------------------------------------------
    public int getIdroles() {
        return idroles;
    }

    public void setIdroles(int idroles) {
        this.idroles = idroles;
    }
    //-------------------------------------------------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

