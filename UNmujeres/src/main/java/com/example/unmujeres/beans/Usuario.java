package com.example.unmujeres.beans;

import java.util.Date;

public class Usuario {
    private int idUsuario;
    private String nombres;
    private String apellidos;
    private String contraseña;
    private int charDNI;
    private String correo;
    private String direccion;
    private boolean estado;
    private int idroles;
    private int idzona;
    private int iddistritos;
    private Date fechaIncorporacion;
    private byte[] foto;
    private String cod_enc;

    public Usuario(String cod_enc, byte[] foto, Date fechaIncorporacion, int iddistritos, int idUsuario, int idroles, boolean estado, int idzona, String direccion, int charDNI, String correo, String contraseña, String apellidos, String nombres) {
        this.cod_enc = cod_enc;
        this.foto = foto;
        this.fechaIncorporacion = fechaIncorporacion;
        this.iddistritos = iddistritos;
        this.idUsuario = idUsuario;
        this.idroles = idroles;
        this.estado = estado;
        this.idzona = idzona;
        this.direccion = direccion;
        this.charDNI = charDNI;
        this.correo = correo;
        this.contraseña = contraseña;
        this.apellidos = apellidos;
        this.nombres = nombres;
    }

    public Usuario() {}

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idusuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getCharDNI() {
        return charDNI;
    }
    public void setCharDNI(int charDNI) {
        this.charDNI = charDNI;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdroles() {
        return idroles;
    }
    public void setIdroles(int idroles) {
        this.idroles = idroles;
    }
    public int getIdzona() {
        return idzona;

    }
    public void setIdzona(int idzona) {
        this.idzona = idzona;
    }

    public Date getFechaIncorporacion() {
        return fechaIncorporacion;
    }
    public void setFechaIncorporacion(Date fechaIncorporacion) {
        this.fechaIncorporacion = fechaIncorporacion;
    }

    public int getIddistritos() {
        return iddistritos;
    }
    public void setIddistritos(int iddistritos) {
        this.iddistritos = iddistritos;
    }

    public byte[] getFoto() {
        return foto;
    }
    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getCod_enc() {
        return cod_enc;
    }
    public void setCod_enc(String cod_enc) {
        this.cod_enc = cod_enc;
    }
}
