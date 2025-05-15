package com.example.unmujeres.daos;

import java.util.ArrayList;

import com.example.unmujeres.beans.RegistroRespuestas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroRespuestasDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/iweb_proy";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public ArrayList<RegistroRespuestas> getByEhf(int idEhf) {
        ArrayList<RegistroRespuestas> registros = new ArrayList<>();

        String sql = "SELECT * FROM registro_respuestas reg INNER JOIN enc_has_formulario ehf ON reg.idenc_has_formulario = ehf.idenc_has_formulario WHERE ehf.idenc_has_formulario = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, idEhf);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RegistroRespuestas reg = new RegistroRespuestas();

                    reg.setIdRegistroRespuestas(rs.getInt("idregistro_respuestas"));
                    reg.setFechaRegistro(rs.getDate("fecha_registro"));
                    reg.setEstado(rs.getString("codigo"));

                    // Obtener asignacion por id
                    EncHasFormularioDAO encHasFormularioDAO = new EncHasFormularioDAO();
                    reg.setEncHasFormulario(encHasFormularioDAO.getById(idEhf));

                    registros.add(reg);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return registros;
    }


}
