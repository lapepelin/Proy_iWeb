package com.example.unmujeres.daos;

import java.sql.*;
import java.util.ArrayList;

import com.example.unmujeres.beans.EncHasFormulario;
import com.example.unmujeres.beans.RegistroRespuestas;

public class RegistroRespuestasDAO extends BaseDAO {

    public ArrayList<RegistroRespuestas> getByEhf(int idEhf) {
        ArrayList<RegistroRespuestas> registros = new ArrayList<>();

        String sql = "SELECT " +
                "    reg.idregistro_respuestas, " +
                "    reg.fecha_registro, " +
                "    reg.estado, " +
                "    ehf.idenc_has_formulario, " +
                "    ehf.idformulario " +
                "FROM " +
                "    registro_respuestas reg " +
                "INNER JOIN " +
                "    enc_has_formulario ehf ON reg.idenc_has_formulario = ehf.idenc_has_formulario " +
                "WHERE " +
                "    ehf.idenc_has_formulario = ?";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, idEhf);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    RegistroRespuestas reg = new RegistroRespuestas();

                    reg.setEstado(rs.getString("estado"));
                    reg.setIdRegistroRespuestas(rs.getInt("idregistro_respuestas"));
                    reg.setFechaRegistro(rs.getDate("fecha_registro"));

                    // Obtener asignacion por id
                    EncHasFormularioDAO encHasFormularioDAO = new EncHasFormularioDAO();
                    reg.setEncHasFormulario(encHasFormularioDAO.getById(idEhf));


                    registros.add(reg);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registros;
    }

    public RegistroRespuestas getById(int id) {

        RegistroRespuestas reg = null;
        String sql = "SELECT * FROM registro_respuestas WHERE idregistro_respuestas = ?";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reg = new RegistroRespuestas();

                    reg.setIdRegistroRespuestas(rs.getInt("idregistro_respuestas"));
                    reg.setFechaRegistro(rs.getDate("fecha_registro"));
                    reg.setEstado(rs.getString("estado"));

                    EncHasFormularioDAO ehfDAO = new EncHasFormularioDAO();
                    reg.setEncHasFormulario(ehfDAO.getById(rs.getInt("idenc_has_formulario")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reg;
    }

    public void save(RegistroRespuestas reg) {}


    public void delete(int id) {
        String sql = "DELETE FROM registro_respuestas WHERE idregistro_respuestas = ?";

        String delRespSQL = "DELETE FROM respuesta WHERE idregistro_respuestas = ?";
        String delRegSQL = "DELETE FROM registro_respuestas WHERE idregistro_respuestas = ?";

        try (Connection con = this.getConnection();){

            try (PreparedStatement ps = con.prepareStatement(delRespSQL)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = con.prepareStatement(delRegSQL)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateState(int id, String estado) {
        String sql = "UPDATE registro_respuestas SET estado = ? WHERE idregistro_respuestas = ?";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);){

            ps.setString(1, estado);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
