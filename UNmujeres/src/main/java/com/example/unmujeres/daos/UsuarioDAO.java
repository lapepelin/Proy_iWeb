package com.example.unmujeres.daos;

import com.example.unmujeres.beans.Formulario;
import com.example.unmujeres.beans.Usuario;

import java.sql.*;

public class UsuarioDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/iweb_proy";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public Usuario getById(int id) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuario WHERE idusuario = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario();

                    usuario.setIdUsuario(rs.getInt("idusuario"));
                    usuario.setNombres(rs.getString("nombres"));
                    usuario.setApellidos(rs.getString("apellidos"));
                    usuario.setFechaIncorporacion(rs.getDate("fecha_incorporacion"));
                    usuario.setEstado(rs.getBoolean("estado"));

                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
