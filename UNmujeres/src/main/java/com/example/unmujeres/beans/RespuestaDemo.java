package com.example.unmujeres.beans;

public class RespuestaDemo {

    private int idrespuesta;
    private String respuesta;
    private RegistroRespuestas registroRespuestas;
    private PreguntaDemo pregunta;

    public RespuestaDemo() {}

    public int getIdrespuesta() {
        return idrespuesta;
    }
    public void setIdrespuesta(int idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public PreguntaDemo getPregunta() {
        return pregunta;
    }
    public void setPregunta(PreguntaDemo pregunta) {
        this.pregunta = pregunta;
    }

    public RegistroRespuestas getRegistroRespuestas() {
        return registroRespuestas;
    }
    public void setRegistroRespuestas(RegistroRespuestas registroRespuestas) {
        this.registroRespuestas = registroRespuestas;
    }
}


