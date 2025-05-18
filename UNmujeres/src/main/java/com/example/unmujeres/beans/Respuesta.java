package com.example.unmujeres.beans;

public class Respuesta {

    private int idRespuesta;
    private String respuesta;
    private Pregunta pregunta;
    private RegistroRespuestas registro;


    public Respuesta() {}

    public int getIdRespuesta() {
        return idRespuesta;
    }
    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }
    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public RegistroRespuestas getRegistro() {
        return registro;
    }
    public void setRegistro(RegistroRespuestas registro) {
        this.registro = registro;
    }
}

