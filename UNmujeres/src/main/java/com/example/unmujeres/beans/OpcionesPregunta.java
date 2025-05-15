package com.example.unmujeres.beans;

public class OpcionesPregunta {

    private int idOpcionesPregunta;
    private String opciones;
    private int preguntaIdPregunta;

    //-------------------------------------------------

    public int getIdOpcionesPregunta() {
        return idOpcionesPregunta;
    }

    public void setIdOpcionesPregunta(int idOpcionesPregunta) {
        this.idOpcionesPregunta = idOpcionesPregunta;
    }
    //-------------------------------------------------
    public String getOpciones() {
        return opciones;
    }

    public void setOpciones(String opciones) {
        this.opciones = opciones;
    }
    //-------------------------------------------------
    public int getPreguntaIdPregunta() {
        return preguntaIdPregunta;
    }

    public void setPreguntaIdPregunta(int preguntaIdPregunta) {
        this.preguntaIdPregunta = preguntaIdPregunta;
    }
}

