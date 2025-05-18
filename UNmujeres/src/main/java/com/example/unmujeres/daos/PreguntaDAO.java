package com.example.unmujeres.daos;

import java.util.ArrayList;

import com.example.unmujeres.beans.Pregunta;
import com.example.unmujeres.beans.Formulario;
import com.example.unmujeres.beans.RegistroRespuestas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreguntaDAO extends BaseDAO{

    public ArrayList<Pregunta> listaPreguntas(int idFormulario) {

        ArrayList<Pregunta> preguntas  = new ArrayList<>();

        String sql = "Select * from pregunta where id=" + idFormulario;



        return preguntas;
    }

}
