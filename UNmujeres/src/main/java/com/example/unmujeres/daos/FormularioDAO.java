package com.example.unmujeres.daos;

import java.util.ArrayList;

import com.example.unmujeres.beans.EncHasFormulario;
import com.example.unmujeres.beans.Formulario;
import com.example.unmujeres.beans.RegistroRespuestas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FormularioDAO extends BaseDAO {


    public ArrayList<EncHasFormulario> listarFormulariosAsignados(int encIdUsuario) {
        ArrayList<EncHasFormulario> Asignacion = new ArrayList<>();

        String sql = "SELECT " +
                "    f.idformulario, " +
                "    f.nombre AS formulario, " +
                "    f.registros_esperados, " +
                "    COUNT(reg.idregistro_respuestas) AS registros_completados, " +
                "    ehf.fecha_asignacion, " +
                "    f.fecha_limite, " +
                "    NULL " +
                "FROM enc_has_formulario ehf " +
                "INNER JOIN formulario f " +
                "    ON ehf.idformulario = f.idformulario " +
                "LEFT JOIN registro_respuestas reg " +
                "    ON ehf.idenc_has_formulario = reg.idenc_has_formulario " +
                "WHERE ehf.enc_idusuario = ? " +
                "    AND (f.estado = '1') " +
                "GROUP BY " +
                "    f.idformulario, " +
                "    f.nombre, " +
                "    f.registros_esperados, " +
                "    ehf.fecha_asignacion, " +
                "    ehf.idenc_has_formulario";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, encIdUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Formulario fa = new Formulario();
                    fa.setIdFormulario(rs.getInt("f.idformulario"));
                    System.out.println("Se consulta el formulario: " + fa.getIdFormulario() + fa.getNombre() );
                    fa.setNombre(rs.getString("nombre"));
                    fa.setRegistrosEsperados(rs.getInt("registros_esperados"));
                    fa.setFechaLimite(rs.getDate("fecha_limite"));

                    EncHasFormulario asig = new EncHasFormulario();
                    asig.setIdEncHasFormulario(rs.getInt("idenc_has_formulario"));
                    asig.setFechaAsignacion(rs.getDate("fecha_asignacion"));
                    asig.setFormulario(fa);

                    RegistroRespuestas reg = new RegistroRespuestas();
                    reg.setIdRegistroRespuestas(rs.getInt("reg.idregistro_respuestas"));
                    reg.setEncHasFormulario(asig);

                    Asignacion.add(asig);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Asignacion;
    }


    public Formulario getById(int id) {
        Formulario formulario = null;
        String sql = "SELECT * FROM formulario WHERE idformulario = ?";

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);){
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    formulario = new Formulario();

                    formulario.setIdFormulario(rs.getInt("idformulario"));
                    formulario.setNombre(rs.getString("nombre"));
                    formulario.setFechaLimite(rs.getDate("fecha_limite"));
                    formulario.setEstado(rs.getBoolean("estado"));
                    formulario.setRegistrosEsperados(rs.getInt("registros_esperados"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formulario;

    }
}
