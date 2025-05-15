package com.example.unmujeres.daos;

import com.example.unmujeres.beans.EncHasFormulario;
import com.example.unmujeres.beans.Formulario;
import com.example.unmujeres.beans.RegistroRespuestas;

import java.sql.*;
import java.util.ArrayList;

public class noFormularioDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/iweb_proy?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static ArrayList<Formulario> listarFormulariosAsignados(int encIdUsuario) {
        ArrayList<Formulario> formsAsignados = new ArrayList<>();

        String sql = "SELECT " +
                "    f.idformulario, " +
                "    f.nombre, " +
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

            ps.setInt(1, 7);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Formulario f = new Formulario();

                    f.setIdFormulario(rs.getInt("idformulario"));
                    f.setNombre(rs.getString("nombre"));
                    f.setRegistrosEsperados(rs.getInt("registros_esperados"));
                    //f.setRegistrosCompletados(rs.getInt("registros_completados"));
                    //f.setFechaAsignacion(rs.getDate("fecha_asignacion"));
                    f.setFechaLimite(rs.getDate("fecha_limite"));

                    System.out.println("📌 Formulario extraído: " + f.getIdFormulario());

                    formsAsignados.add(f);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return formsAsignados;
    }

//    public Formulario buscarFormulario(int idformulario) {
//
//        Formulario form = null;
//        String sql =
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
//            PreparedStatement ps = con.prepareStatement(sql);
//
//            ps.setInt(1, idformulario);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    Formulario f = new Formulario();
//
//                    f.setIdFormulario(rs.getInt("idformulario"));
//                    f.setNombre(rs.getString("nombre"));
//                    f.setRegistrosEsperados(rs.getInt("registros_esperados"));
//                    f.setRegistrosCompletados(rs.getInt("registros_completados"));
//                    f.setFechaAsignacion(rs.getDate("fecha_asignacion"));
//                    f.setFechaLimite(rs.getDate("fecha_limite"));
//
//                    System.out.println("📌 Formulario extraído: " + f.getIdFormulario());
//
//                    formsAsignados.add(f);
//                }
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return form;
//    }
}