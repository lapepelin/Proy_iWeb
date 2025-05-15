package com.example.onu.beans;

public class Respuesta {

    private int idrespuesta;
    private String respuesta;
    private int pregunta_idregunta;
    private int reg_respuestas_idregistro_respuestas;

    //-------------------------------------------------
    public Respuesta() {}

    //-------------------------------------------------
    public int getIdrespuesta() {
        return idrespuesta;
    }

    public void setIdrespuesta(int idrespuesta) {
        this.idrespuesta = idrespuesta;
    }
    //-------------------------------------------------
    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    //-------------------------------------------------
    public int getPregunta_idregunta() {
        return pregunta_idregunta;
    }

    public void setPregunta_idpregunta(int pregunta_idpregunta) {
        this.pregunta_idregunta = pregunta_idpregunta;
    }
    //-------------------------------------------------
    public int getReg_respuestas_idregistro_respuestas() {
        return reg_respuestas_idregistro_respuestas;
    }

    public void setReg_respuestas_idregistro_respuestas(int reg_respuestas_idregistro_respuestas) {
        this.reg_respuestas_idregistro_respuestas = reg_respuestas_idregistro_respuestas;
    }
}

