package com.example.unmujeres.beans;

import com.example.unmujeres.beans.Categoria;

import java.util.Date;

public class Formulario {
    private int idFormulario;
    private String nombre;
    private Date fechaCreacion;
    private Date fechaLimite;
    private boolean estado;
    private int registrosEsperados;
    private int idCategoria;
    //Categoria categoria;
    //
    //private int registrosCompletados;
    //private Date fechaAsignacion;

    //
    public Formulario(int idFormulario, String nombre, Date fechaCreacion, Date fechaLimite, boolean estado, int registrosEsperados, int idCategoria, int registrosCompletados, Date fechaAsignacion) {
        this.idFormulario = idFormulario;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
        this.registrosEsperados = registrosEsperados;
        this.idCategoria = idCategoria;
        //this.categoria = categoria;
        //this.registrosCompletados = registrosCompletados;
        //this.fechaAsignacion = fechaAsignacion;
    }

    public Formulario(){
    }

    public int getIdFormulario() {
        return idFormulario;
    }
    public void setIdFormulario(int idFormulario) {
        this.idFormulario = idFormulario;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }
    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public int getRegistrosEsperados() {
        return registrosEsperados;
    }
    public void setRegistrosEsperados(int registrosEsperados) {
        this.registrosEsperados = registrosEsperados;
    }

    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

//    public Categoria getCategoria() {
//        return categoria;
//    }
//    public void setCategoria(Categoria categoria) {
//        this.categoria = categoria;
//    }
    public int getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

//    public int getRegistrosCompletados() {
//        return registrosCompletados;
//    }
//    public void setRegistrosCompletados(int registrosCompletados) {
//        this.registrosCompletados = registrosCompletados;
//    }
//
//    public Date getFechaAsignacion() {
//        return fechaAsignacion;
//    }
//    public void setFechaAsignacion(Date fechaAsignacion) {
//        this.fechaAsignacion = fechaAsignacion;
//    }
}
