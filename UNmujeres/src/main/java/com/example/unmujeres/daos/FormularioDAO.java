package com.example.unmujeres.daos;

import java.util.ArrayList;

import com.example.unmujeres.beans.EncHasFormulario;
import com.example.unmujeres.beans.Formulario;
import com.example.unmujeres.beans.RegistroRespuestas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;








public class FormularioDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/iweb_proy";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public ArrayList<Formulario> listarFormulariosAsignados(int encIdUsuario) {
        ArrayList<Formulario> formsAsignados = new ArrayList<>();

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

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, encIdUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Formulario fa = new Formulario();
                    EncHasFormulario asig = new EncHasFormulario();
                    RegistroRespuestas reg = new RegistroRespuestas();

                    fa.setIdFormulario(rs.getInt("idformulario"));
                    fa.setNombre(rs.getString("nombres"));
                    fa.setRegistrosEsperados(rs.getInt("registros_esperados"));
                    //reg.setRegistrosCompletados(rs.getInt("registros_completados"));
                    asig.setFechaAsignacion(rs.getDate("fecha_asignacion"));
                    fa.setFechaLimite(rs.getDate("fecha_limite"));
                    formsAsignados.add(fa);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return formsAsignados;
    }


    public Formulario getById(int id) {
        Formulario formulario = null;
        String sql = "SELECT * FROM formulario WHERE idformulario = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    formulario = new Formulario();

                    formulario.setIdFormulario(rs.getInt("idformulario"));
                    formulario.setNombre(rs.getString("nombres"));
                    formulario.setFechaLimite(rs.getDate("fecha_limite"));
                    formulario.setEstado(rs.getBoolean("estado"));
                    formulario.setRegistrosEsperados(rs.getInt("registros_esperados"));

                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return formulario;

    }
}
