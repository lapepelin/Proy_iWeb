package com.example.unmujeres.servlets.coordinador;

import com.example.unmujeres.daos.GestionFormDao;
import com.example.unmujeres.daos.CategoriaDAO;
import com.example.unmujeres.beans.Categoria;
import com.example.unmujeres.dtos.FormularioDto;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@WebServlet(name = "GestionFormServlet", value = "/coordinador/GestionFormServlet")
public class GestionFormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        GestionFormDao formularioDao = new GestionFormDao();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        ArrayList<Categoria> categorias = categoriaDAO.getCategorias();
        request.setAttribute("categorias", categorias);

        String catParam = request.getParameter("idCategoria");
        int idCategoria = 0;
        if (catParam != null && !catParam.isEmpty()) {
            try {
                idCategoria = Integer.parseInt(catParam);
            } catch (NumberFormatException ignored) {
            }
        }

        ArrayList<FormularioDto> list = formularioDao.listar(idCategoria);

        request.setAttribute("selectedCategoria", idCategoria);
        request.setAttribute("lista", list);
        RequestDispatcher rd = request.getRequestDispatcher("/coordinador/gestionFormularios.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GestionFormDao gestionFormDao = new GestionFormDao();

        String idParam = request.getParameter("id");
        String action = request.getParameter("action"); // <-- Obtener el parámetro 'action'

        String message = "";
        String type = "error"; // Por defecto, si algo sale mal

        if (idParam == null || idParam.isEmpty() || action == null || action.isEmpty()) {
            message = "Parámetros incompletos para cambiar el estado del formulario.";
        } else {
            try {
                int idFormulario = Integer.parseInt(idParam);
                boolean nuevoEstado;

                if ("activar".equals(action)) {
                    nuevoEstado = true;
                    if (gestionFormDao.cambiarEstado(idFormulario, nuevoEstado)) {
                        message = "Formulario activado exitosamente.";
                        type = "success";
                    } else {
                        message = "Error al activar el formulario.";
                    }
                } else if ("desactivar".equals(action)) {
                    nuevoEstado = false;
                    if (gestionFormDao.cambiarEstado(idFormulario, nuevoEstado)) {
                        message = "Formulario desactivado exitosamente.";
                        type = "success";
                    } else {
                        message = "Error al desactivar el formulario.";
                    }
                } else {
                    message = "Acción no reconocida para el cambio de estado.";
                }

            } catch (NumberFormatException e) {
                message = "ID de formulario inválido.";
            } catch (Exception e) {
                // Captura cualquier otra excepción que pueda ocurrir en el DAO
                message = "Ocurrió un error inesperado: " + e.getMessage();
                e.printStackTrace(); // Imprime el stack trace para depuración
            }
        }

        // Redirigir de vuelta a la página de gestión de formularios con el mensaje
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

        if ("success".equals(type)) {
            response.sendRedirect(request.getContextPath() + "/coordinador/GestionFormServlet?success=" + encodedMessage);
        } else {
            response.sendRedirect(request.getContextPath() + "/coordinador/GestionFormServlet?error=" + encodedMessage);
        }
    }
}
