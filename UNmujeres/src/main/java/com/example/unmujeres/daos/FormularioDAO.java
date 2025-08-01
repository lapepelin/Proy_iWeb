package com.example.unmujeres.daos;

import java.sql.*;
import java.util.ArrayList;

import com.example.unmujeres.beans.Formulario;
import com.example.unmujeres.dtos.ContenidoReporteDTO;
import com.example.unmujeres.dtos.ReporteDTO;

import java.util.List;

public class FormularioDAO extends BaseDAO {

    public Formulario getById(int id) {
        Formulario formulario = null;
        String sql = "SELECT * FROM formulario WHERE idformulario = ? AND estado = 1";

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

    public ArrayList<ReporteDTO> getReportes(int idZona, int idRol, String fechaInicio, String fechaFin) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    DISTINCT f.idformulario, " +
                        "    f.nombre AS nombre_formulario, " +
                        "    COUNT(r.idregistro_respuestas) AS total_respuestas " +
                        "FROM " +
                        "    formulario f " +
                        "INNER JOIN enc_has_formulario ehf ON f.idformulario = ehf.idformulario " +
                        "INNER JOIN usuario u ON ehf.enc_idusuario = u.idusuario " +
                        "INNER JOIN registro_respuestas r ON ehf.idenc_has_formulario = r.idenc_has_formulario " +
                        "WHERE r.estado = 'C' ");

        // Lista para guardar los parámetros a asignar en el PreparedStatement
        List<Object> parametros = new ArrayList<>();

        // Filtro para rol fijo (si fuera necesario) o se utiliza idRol
        if(idRol != 0) {
            sql.append(" AND u.idroles = ? ");
            parametros.add(idRol);
        }
        // Filtro de zona: solo se agrega si el id es distinto de 0 (0 equivale a "Todos")
        if(idZona != 0) {
            sql.append(" AND u.idzona = ? ");
            parametros.add(idZona);
            System.out.println("Parametro de zona: " + idZona);
        }
        // Filtro para el rango de fechas, si ambos se proporcionan
        if(fechaInicio != null && !fechaInicio.trim().isEmpty() &&
                fechaFin != null && !fechaFin.trim().isEmpty()) {
            sql.append(" AND (r.fecha_registro BETWEEN ? AND ?) ");
            parametros.add(java.sql.Date.valueOf(fechaInicio));
            parametros.add(java.sql.Date.valueOf(fechaFin));
        }
        sql.append(" GROUP BY f.idformulario, f.nombre ");
        sql.append(" ORDER BY f.idformulario ASC ");

        ArrayList<ReporteDTO> reportes = new ArrayList<>();

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(String.valueOf(sql));) {

            int index = 1;
            for (Object param : parametros) {
                ps.setObject(index++, param);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReporteDTO reporte = new ReporteDTO();
                    System.out.println("se extrajo el form: " + rs.getInt(1));
                    reporte.setIdFormulario(rs.getInt(1));
                    reporte.setNombreFormulario(rs.getString(2));
                    reporte.setTotalRegistros(rs.getInt(3));
                    // Rellenar los parámetros de filtro en el DTO para mostrarlos en la vista.
                    reporte.setFechaInicio(fechaInicio);
                    reporte.setFechaFin(fechaFin);
                    reporte.setIdZona(idZona);
                    reporte.setIdRol(idRol);

                    reportes.add(reporte);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reportes;
    }


    public ArrayList<ContenidoReporteDTO> getContenidoReporte(int idForm, int idZona, int idRol, String fechaInicio, String fechaFin) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    r.idrespuesta, " +
                        "    r.respuesta, " +
                        "    r.idpregunta, " +
                        "    r.idregistro_respuestas, " +
                        "    u.cod_enc " +
                        "FROM respuesta r " +
                        "INNER JOIN registro_respuestas reg ON r.idregistro_respuestas = reg.idregistro_respuestas " +
                        "INNER JOIN enc_has_formulario ehf ON reg.idenc_has_formulario = ehf.idenc_has_formulario " +
                        "INNER JOIN usuario u ON ehf.enc_idusuario = u.idusuario " +
                        "WHERE reg.estado = 'C' " +
                        "AND ehf.idformulario = ? ");

        // Lista para guardar los parámetros a asignar en el PreparedStatement
        List<Object> parametros = new ArrayList<>();
        parametros.add(idForm);

        // Filtro para rol fijo (si fuera necesario) o se utiliza idRol
        if(idRol != 0) {
            sql.append(" AND u.idroles = ? ");
            parametros.add(idRol);
        }
        // Filtro de zona: solo se agrega si el id es distinto de 0 (0 equivale a "Todos")
        if(idZona != 0) {
            sql.append(" AND u.idzona = ? ");
            parametros.add(idZona);
            System.out.println("Parametro de zona: " + idZona);
        }
        // Filtro para el rango de fechas, si ambos se proporcionan
        if(fechaInicio != null && !fechaInicio.trim().isEmpty() &&
                fechaFin != null && !fechaFin.trim().isEmpty()) {
            sql.append(" AND (reg.fecha_registro BETWEEN ? AND ?) ");
            parametros.add(java.sql.Date.valueOf(fechaInicio));
            parametros.add(java.sql.Date.valueOf(fechaFin));
        }
        sql.append(" ORDER BY reg.idregistro_respuestas ASC");

        ArrayList<ContenidoReporteDTO> contenido = new ArrayList<>();

        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(String.valueOf(sql));) {

            int index = 1;
            for (Object param : parametros) {
                ps.setObject(index++, param);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ContenidoReporteDTO item = new ContenidoReporteDTO();
                    item.setIdRespuesta(rs.getInt(1));
                    item.setRespuesta(rs.getString(2));
                    item.setIdPregunta(rs.getInt(3));
                    item.setIdRegistro(rs.getInt(4));
                    item.setCodEnc(rs.getString(5));

                    contenido.add(item);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return contenido;
    }

    public int crearFormulario(Connection conn, Formulario form) throws SQLException {

        String sql = "INSERT INTO formulario (nombre, fecha_creacion, fecha_limite, estado, registros_esperados, idcategoria) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = this.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, form.getNombre());
            ps.setDate(2, new Date(form.getFechaCreacion().getTime()));
            ps.setDate(3, new Date(form.getFechaLimite().getTime()));
            ps.setInt(4, 1);
            ps.setInt(5, form.getRegistrosEsperados());
            ps.setInt(6, form.getCategoria().getIdCategoria());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idNuevoForm = rs.getInt(1);
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo crear un nuevo formulario");
    }

    public ArrayList<Formulario> getFormularios() {
        ArrayList<Formulario> formularios = new ArrayList<>();

        String sql = "SELECT * FROM formulario";
        try (Connection con = this.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Formulario formulario = new Formulario();
                formulario.setIdFormulario(rs.getInt(1));
//                formulario.setNombre(rs.getString(2));
//                formulario.setFechaCreacion(rs.getDate(3));
//                formulario.setFechaLimite(rs.getDate(4));

                formularios.add(formulario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return formularios;
    }

}
