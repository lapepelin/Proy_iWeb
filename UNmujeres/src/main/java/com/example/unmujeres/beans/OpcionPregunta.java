package com.example.unmujeres.beans;

public class OpcionPregunta {

    private int idOpcionPregunta;
    private String opcion;
    private Pregunta pregunta;

    public int getIdOpcionPregunta() {
        return idOpcionPregunta;
    }
    public void setIdOpcionPregunta(int idOpcionPregunta) {
        this.idOpcionPregunta = idOpcionPregunta;
    }

    public String getOpcion() {
        return opcion;
    }
    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }
}

