package com.example.unmujeres.beans;

public class Pregunta {
    private int idpregunta;
    private String enunciado;
    private String tipo_dato;
    private Seccion seccion;

    public Pregunta() {
    }

    public int getIdpregunta() {
        return idpregunta;
    }
    public void setIdpregunta(int idpregunta) {
        this.idpregunta = idpregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTipo_dato() {
        return tipo_dato;
    }
    public void setTipo_dato(String tipo_dato) {
        this.tipo_dato = tipo_dato;
    }

    public Seccion getSeccion() {
        return seccion;
    }
    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }
}
