package com.example.unmujeres.beans;
import java.util.Date;

public class RegistroRespuestas {

    private int idRegistroRespuestas;
    private Date fechaRegistro;
    private String estado;
    private EncHasFormulario encHasFormulario;

    //
    public RegistroRespuestas() {
    }

    public int getIdRegistroRespuestas() {
        return idRegistroRespuestas;
    }
    public void setIdRegistroRespuestas(int idRegistroRespuestas) {
        this.idRegistroRespuestas = idRegistroRespuestas;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public EncHasFormulario getEncHasFormulario() {
        return encHasFormulario;
    }
    public void setEncHasFormulario(EncHasFormulario encHasFormulario) {
        this.encHasFormulario = encHasFormulario;
    }
}

