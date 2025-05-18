package com.example.unmujeres.beans;

public class Pregunta {
    private int idPregunta;
    private String enunciado;
    private String tipoDato;
    private Seccion seccion;

    public Pregunta() {
    }

    public int getIdPregunta() {
        return idPregunta;
    }
    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getEnunciado() {
        return enunciado;
    }
    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTipoDato() {
        return tipoDato;
    }
    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public Seccion getSeccion() {
        return seccion;
    }
    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }
}
