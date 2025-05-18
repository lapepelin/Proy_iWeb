package com.example.unmujeres.daos;

import java.util.ArrayList;

import com.example.unmujeres.beans.EncHasFormulario;
import com.example.unmujeres.beans.Formulario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EncHasFormularioDAO extends BaseDAO {

    public ArrayList<EncHasFormulario> getByEncuestador(int idEnc) {
        ArrayList<EncHasFormulario> asignaciones = new ArrayList<>();

        String sql = "SELECT * FROM enc_has_formulario WHERE enc_idusuario = ?";

        try (Connection con = this.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, idEnc);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EncHasFormulario a = new EncHasFormulario();

                    a.setIdEncHasFormulario(rs.getInt("idenc_has_formulario"));
                    a.setFechaAsignacion(rs.getDate("fecha_asignacion"));
                    a.setCodigo(rs.getString("codigo"));

                    // Obtener formulario por id
                    FormularioDAO formularioDAO = new FormularioDAO();
                    a.setFormulario(formularioDAO.getById(rs.getInt("idformulario")));

                    // Obtener encuestador por id
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    a.setUsuario(usuarioDAO.getById(idEnc));

                    asignaciones.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return asignaciones;
    }


    public EncHasFormulario getById(int id) {
        EncHasFormulario asig = null;
        String sql = "SELECT * FROM enc_has_formulario WHERE idenc_has_formulario = ?";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    asig = new EncHasFormulario();

                    asig.setIdEncHasFormulario(rs.getInt("idenc_has_formulario"));
                    asig.setFechaAsignacion(rs.getDate("fecha_asignacion"));
                    asig.setCodigo(rs.getString("codigo"));
                    // Obtener formulario por id
                    FormularioDAO formularioDAO = new FormularioDAO();
                    asig.setFormulario(formularioDAO.getById(rs.getInt("idformulario")));
                    // Obtener encuestador por id
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    asig.setUsuario(usuarioDAO.getById(rs.getInt("enc_idusuario")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asig;
    }

//    public EncHasFormulario> getByEhf(int idEnc) {
//
//    }
}
