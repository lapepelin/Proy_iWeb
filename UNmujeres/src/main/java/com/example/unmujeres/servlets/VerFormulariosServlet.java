package com.example.unmujeres.servlets;

import com.example.unmujeres.beans.EncHasFormulario;
import com.example.unmujeres.beans.Formulario;
import com.example.unmujeres.beans.RegistroRespuestas;
import com.example.unmujeres.daos.EncHasFormularioDAO;
import com.example.unmujeres.daos.FormularioDAO;
import com.example.unmujeres.daos.RegistroRespuestasDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.LinkedHashMap;

@WebServlet(
        name = "VerFormulariosServlet",
        value = {"/VerFormulariosServlet", "/ServletA"}, // Múltiples rutas de acceso
        initParams = {
                @WebInitParam(name = "config", value = "default") // Parámetro de configuración
        }
)
public class VerFormulariosServlet extends HttpServlet {

    private String configValue;

    @Override
    public void init() throws ServletException {
        // Cargar parámetro de configuración
        configValue = getServletConfig().getInitParameter("config");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        FormularioDAO formsAsig = new FormularioDAO();
        RequestDispatcher view;

        switch (action) {

            case "lista":
                int idEnc = 7;  //Integer.parseInt(request.getParameter("id"));
                //EncHasFormularioDAO ehfDAO = new EncHasFormularioDAO();
                //ArrayList<EncHasFormulario> asignaciones = ehfDAO.getByEncuestador(idEnc);

                //ArrayList<Formulario> formularios = formsAsig.listarFormulariosAsignados(idEnc);

                try {
                    // 1. Obtener DAOs
                    EncHasFormularioDAO ehfDAO = new EncHasFormularioDAO();
                    RegistroRespuestasDAO registroDAO = new RegistroRespuestasDAO();
                    FormularioDAO formularioDAO = new FormularioDAO();

                    // 2. Obtener asignaciones
                    ArrayList<EncHasFormulario> asignaciones = ehfDAO.getByEncuestador(7); // ID hardcodeado

                    // 3. Estructura para vista
                    ArrayList<Map<String, Object>> datos = new ArrayList<>();

                    for (EncHasFormulario asignacion : asignaciones) {
                        // 4. Obtener formulario relacionado
                        Formulario formulario = formularioDAO.getById(asignacion.getFormulario().getIdFormulario());


                        if (formulario != null && formulario.isEstado() == 1) {

                            Map<String, Object> item = new LinkedHashMap<>();

                            // 5. Datos formulario
                            item.put("id_formulario", formulario.getIdFormulario());
                            item.put("nombre_formulario", formulario.getNombre());
                            item.put("fecha_limite", formulario.getFechaLimite());

                            // 7. Datos asignacion ehf
                            item.put("fecha_asignacion", asignacion.getFechaAsignacion());

                            // 8. Datos registro
                            
                            item.put("registros", size(asignaciones);




                            datos.add(item);
                        }
                    }

                    // 9. Enviar a vista
                    request.setAttribute("datos", datos);
                    request.getRequestDispatcher("/WEB-INF/vistas/listado.jsp").forward(request, response);

                } catch (Exception e) {
                    request.setAttribute("error", "Error: " + e.getMessage());
                    request.getRequestDispatcher("/WEB-INF/vistas/error.jsp").forward(request, response);
                }

                break;
            case "formulario":
                // Codigo de Oshin
                break;

            case "editar":
                String idformulario = request.getParameter("id");
                //Formulario form = FormularioDAO.
                break;

        }


        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        RequestDispatcher view;

        switch (action) {
            case "crearReg":

                break;
            case "editarReg":
                //String jobId = request.getParameter("id");
                //Job Job = jobDao.obtenerTrabajo(jobId);
                //if(Job == null){
                //    response.sendRedirect(request.getContextPath() + "/Servlet");
                //}else{
                //    request.setAttribute("trabajo",Job);
                break;
        }



        request.setCharacterEncoding("UTF-8");

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Ejemplo básico para otros métodos HTTP
        resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}