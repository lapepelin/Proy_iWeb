package com.example.unmujeres.beans;

import java.util.Date;

public class EncHasFormulario {
    private int idEncHasFormulario;
    private Formulario formulario;
    private Usuario usuario;
    private String codigo;
    private Date fechaAsignacion;

    public EncHasFormulario() {
    }

    public EncHasFormulario(int idEncHasFormulario) {
        this.idEncHasFormulario = idEncHasFormulario;
    }

    public int getIdEncHasFormulario() {
        return idEncHasFormulario;
    }
    public void setIdEncHasFormulario(int idEncHasFormulario) {
        this.idEncHasFormulario = idEncHasFormulario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Formulario getFormulario() {
        return formulario;
    }
    public void setFormulario(Formulario formulario) {
        this.formulario = formulario;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }
    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }
};



