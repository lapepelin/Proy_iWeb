package com.example.onu.beans;

public class Seccion {
    private int idseccion;
    private String nombre_sec; // nombre_sec tal como en la base de datos
    private int formulario_idformulario;
    //-------------------------------------------------
    public Seccion() {}
    //-------------------------------------------------
    public Seccion(int idseccion, String nombre_sec, int formulario_idformulario) {
        this.idseccion = idseccion;
        this.nombre_sec = nombre_sec;
        this.formulario_idformulario = formulario_idformulario;
    }
    //-------------------------------------------------
    public int getIdseccion() {
        return idseccion;
    }

    public void setIdseccion(int idseccion) {
        this.idseccion = idseccion;
    }
    //-------------------------------------------------
    public String getNombre_sec() {
        return nombre_sec;
    }

    public void setNombre_sec(String nombre_sec) {
        this.nombre_sec = nombre_sec;
    }
    //-------------------------------------------------
    public int getFormulario_idformulario() {
        return formulario_idformulario;
    }

    public void setFormulario_idformulario(int formulario_idformulario) {
        this.formulario_idformulario = formulario_idformulario;
    }
}


