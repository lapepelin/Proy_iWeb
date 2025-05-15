package com.example.unmujeres.beans;

public class Pregunta {
    private int idpregunta;
    private String enunciado;
    private String tipo_dato;
    private int seccion_idseccion;
    //private Seccion seccion;


    public Pregunta() {}
    //-------------------------------------------------
    public Pregunta(int idpregunta, String enunciado, String tipo_dato, int seccion_idseccion) {
        this.idpregunta = idpregunta;
        this.enunciado = enunciado;
        this.tipo_dato = tipo_dato;
        this.seccion_idseccion = seccion_idseccion;
    }
    //-------------------------------------------------
    public int getIdpregunta() {
        return idpregunta;
    }

    public void setIdpregunta(int idpregunta) {
        this.idpregunta = idpregunta;
    }
    //-------------------------------------------------
    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }
    //-------------------------------------------------
    public String getTipo_dato() {
        return tipo_dato;
    }

    public void setTipo_dato(String tipo_dato) {
        this.tipo_dato = tipo_dato;
    }
    //-------------------------------------------------
    public int getSeccion_idseccion() {
        return seccion_idseccion;
    }

    public void setSeccion_idseccion(int seccion_idseccion) {
        this.seccion_idseccion = seccion_idseccion;
    }
}
