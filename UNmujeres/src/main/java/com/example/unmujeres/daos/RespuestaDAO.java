package com.example.unmujeres.daos;

import com.example.unmujeres.beans.Pregunta;
import com.example.unmujeres.beans.Respuesta;
import com.example.unmujeres.beans.Seccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RespuestaDAO extends BaseDAO{

    public ArrayList<Respuesta> listaRespuestas(int idRegistro) {

        ArrayList<Respuesta> respuestas  = new ArrayList<>();

        String sql = "SELECT " +
                "    p.idpregunta, " +
                "    p.enunciado, " +
                "    p.tipo_dato, " +
                "    r.idrespuesta, " +
                "    r.respuesta, " +
                "    s.idseccion, " +
                "    s.nombre_sec " +
                "FROM " +
                "    pregunta p " +
                "LEFT JOIN " +
                "    respuesta r ON p.idpregunta = r.idpregunta " +
                "INNER JOIN " +
                "    seccion s ON p.idseccion = s.idseccion " +
                "WHERE " +
                "    r.idregistro_respuestas = ?";
        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, idRegistro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Respuesta respuesta = new Respuesta();
                    respuesta.setIdRespuesta(rs.getInt("idrespuesta"));
                    respuesta.setRespuesta(rs.getString("respuesta"));
                    //respuesta.setRegistro(idRegistro);
                        Pregunta pregunta = new Pregunta();
                        pregunta.setIdPregunta(rs.getInt("idpregunta"));
                        pregunta.setEnunciado(rs.getString("enunciado"));
                        pregunta.setTipoDato(rs.getString("tipo_dato"));
                            Seccion seccion = new Seccion();
                            seccion.setIdSeccion(rs.getInt("idseccion"));
                            seccion.setNombreSec(rs.getString("nombre_sec"));

                        pregunta.setSeccion(seccion);

//                        OpcionPregunta opciones = new OpcionPregunta();
//                        opciones.setIdOpcionesPregunta(rs.getInt("idopciones_pregunta"));
//                        opciones.setOpcion(rs.getString("opciones"));
//                        opciones.setPregunta(pregunta);

                    respuesta.setPregunta(pregunta);

                    respuestas.add(respuesta);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return respuestas;
    }


    public void updateResponse(int idReg,int idRespuesta,String respuesta) {

        String sql="UPDATE respuesta SET respuesta= ? WHERE idrespuesta= ? AND idregistro_respuestas= ?";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, respuesta);
            ps.setInt(2, idRespuesta);
            ps.setInt(3, idReg);

            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
