package com.example.unmujeres.beans;

public class Seccion {
    private int idSeccion;
    private String nombreSec; // nombreSec tal como en la base de datos
    private Formulario formulario;

    public Seccion() {}

    public int getIdSeccion() {
        return idSeccion;
    }
    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getNombreSec() {
        return nombreSec;
    }
    public void setNombreSec(String nombreSec) {
        this.nombreSec = nombreSec;
    }

    public Formulario getFormulario() {
        return formulario;
    }
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }
}


