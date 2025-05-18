package com.example.unmujeres.daos;

import java.sql.*;
import java.util.ArrayList;

import com.example.unmujeres.beans.EncHasFormulario;
import com.example.unmujeres.beans.RegistroRespuestas;

public class RegistroRespuestasDAO extends BaseDAO {

    public ArrayList<RegistroRespuestas> getByEhf(int idEhf) {
        ArrayList<RegistroRespuestas> registros = new ArrayList<>();

        String sql = "SELECT * FROM registro_respuestas reg INNER JOIN enc_has_formulario ehf ON reg.idenc_has_formulario = ehf.idenc_has_formulario WHERE ehf.idenc_has_formulario = ?";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, idEhf);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegistroRespuestas reg = new RegistroRespuestas();

                    reg.setIdRegistroRespuestas(rs.getInt("idregistro_respuestas"));
                    reg.setFechaRegistro(rs.getDate("fecha_registro"));
                    reg.setEstado(rs.getString("estado"));

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

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);){

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateState(int id, String estado) {
        String sql = "UPDATE registro_respuestas SET estado = ? WHERE idregistro_respuestas = ?";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);){

            ps.setInt(1, id);
            ps.setString(2, estado);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
