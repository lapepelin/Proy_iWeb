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
        int idEnc = 7;

        EncHasFormularioDAO ehfDAO = new EncHasFormularioDAO();
        RegistroRespuestasDAO registroDAO = new RegistroRespuestasDAO();
        FormularioDAO formularioDAO = new FormularioDAO();
        RequestDispatcher view;

        switch (action) {

            case "lista2":

                try {
                    System.out.println("Se consulto lista de asignados metodo 2");
                    //FormularioDAO formularioDAO = new FormularioDAO();

                    ArrayList<EncHasFormulario> data = formularioDAO.listarFormulariosAsignados(idEnc);

                    request.setAttribute("datos", data);
                    view = request.getRequestDispatcher("/showAssignedForms.jsp");
                    view.forward(request, response);

                } catch (ServletException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "lista":
                //int idEnc = 7;
                try {
                    System.out.println("Se consulto lista de asignados metodo 1");

                    // 2. Inicializa arreglo de datos para vista
                    ArrayList<Map<String, Object>> datos = new ArrayList<>();

                    // 3. Obtener arreglo de asignaciones
                    ArrayList<EncHasFormulario> asignaciones = ehfDAO.getByEncuestador(idEnc); // ID hardcodeado
                    //  para cada asignacion
                    for (EncHasFormulario asignacion : asignaciones) {
                        //System.out.println("\n1. Asignacion extraída: " + asignacion.getIdEncHasFormulario());
                        // 4. Obtener formulario relacionado por el id, obtenido de la asignacion
                        Formulario formulario = formularioDAO.getById(asignacion.getFormulario().getIdFormulario());
                        // si existe formulario y esta activo (formulario.estado=1),
                        if (formulario != null && formulario.isEstado()) {
                            //System.out.println("2. Formulario de asignacion existe y activo: " + formulario.getIdFormulario());
                            // inicializa un item para agregar a datos
                            Map<String, Object> item = new LinkedHashMap<>();
                            // 5. Informacion de formulario
                            item.put("id_formulario", formulario.getIdFormulario());
                            item.put("nombre_formulario", formulario.getNombre());
                            item.put("fecha_limite", formulario.getFechaLimite());
                            item.put("registros_esperados", formulario.getRegistrosEsperados());

                            // 7. Datos asignacion ehf
                            item.put("fecha_asignacion", asignacion.getFechaAsignacion());
                            //System.out.println("3. Fecha de asignacion: " + asignacion.getFechaAsignacion());

                            // 8. Datos registro, arreglo de registros en asignacion
                            ArrayList<RegistroRespuestas> registros = registroDAO.getByEhf(asignacion.getIdEncHasFormulario());
                            item.put("registros_completados", registros.size());
                            //System.out.println("4. Cantidad registros completados: " + registros.size());

                            datos.add(item);
                        }
                    }

                    // 9. Enviar a vista
                    request.setAttribute("datos", datos);
                    view = request.getRequestDispatcher("/showAssignedForms.jsp");
                    view.forward(request, response);

                } catch (Exception e) {
                    e.printStackTrace();
                    //request.getRequestDispatcher("/WEB-INF/vistas/error.jsp").forward(request, response);
                }
                break;
            case "formulario":
                // Codigo de Oshin
                break;

            case "historial":
                System.out.println("Se consulto historial");
                //int idEnc = 7;
                try {

                    // 2. Inicializar arreglos de datos (borradores y completados)
                    ArrayList<Map<String, Object>> datos1 = new ArrayList<>();
                    ArrayList<Map<String, Object>> datos2 = new ArrayList<>();

                    // 3. lista de borradores
                    ArrayList<EncHasFormulario> asignaciones = ehfDAO.getByEncuestador(idEnc); // ID hardcodeado
                    for (EncHasFormulario asignacion : asignaciones) {
                        // 3.1. Informacion de registro, arreglo de registros en asignacion
                        ArrayList<RegistroRespuestas> registros = registroDAO.getByEhf(asignacion.getIdEncHasFormulario());
                        // 3.2. Obtener formulario relacionado por el id, obtenido de la asignacion
                        Formulario formulario = formularioDAO.getById(asignacion.getFormulario().getIdFormulario());
                        // para cada registro
                        for (RegistroRespuestas registro : registros) {
                            // 3.3. Validar estado de formulario y estado borrador de registro
                            if (registro != null && registro.getEstado().charAt(0)=='B' && formulario != null && formulario.isEstado()) {
                                // inicializa un item para agregar a datos
                                Map<String, Object> item1 = new LinkedHashMap<>();
                                // 3.5 Informacion de registro
                                item1.put("fecha_registro", registro.getFechaRegistro());
                                item1.put("id_registro", registro.getIdRegistroRespuestas()); // no se mostrara en vista, pero se usara para editar o descartar

                                // 3.4. Informacion de formulario
                                item1.put("id_formulario", formulario.getIdFormulario());
                                item1.put("nombre_formulario", formulario.getNombre());
                                item1.put("fecha_limite", formulario.getFechaLimite());

                                datos1.add(item1);
                            } else if (registro != null && registro.getEstado().charAt(0)=='C' && formulario != null && formulario.isEstado()) {
                                // inicializa un item para agregar a datos
                                Map<String, Object> item2 = new LinkedHashMap<>();
                                // 3.5 Informacion de registro
                                item2.put("fecha_registro", registro.getFechaRegistro());
                                item2.put("id_registro", registro.getIdRegistroRespuestas()); // se deberia mstrar?

                                // 3.4. Informacion de formulario
                                item2.put("id_formulario", formulario.getIdFormulario());
                                item2.put("nombre_formulario", formulario.getNombre());
                                datos2.add(item2);
                            }
                        }
                        // 9. Pasar a vista
                        request.setAttribute("drafts", datos1);
                        request.setAttribute("records", datos2);
                    }

                    // 4. lista de completados

                    // unir con anterior for?

                    view = request.getRequestDispatcher("/responsesHistory.jsp");
                    view.forward(request, response);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "editar":
                int idReg = Integer.parseInt(request.getParameter("id"));

                RegistroRespuestas registro = registroDAO.getById(idReg);

                if (registro == null) {
                    response.sendRedirect(request.getContextPath() + "/VerFormulariosServlet");
                } else {
                    request.setAttribute("registro", registro);
                    view = request.getRequestDispatcher("/2");
                    view.forward(request, response);
                }
                break;

            case "descartar":
                int IdReg = Integer.parseInt(request.getParameter("id"));

                try {

                    if(registroDAO.getById(IdReg) != null){
                        registroDAO.delete(IdReg);
                    }
                    response.sendRedirect(request.getContextPath()+"/VerFormulariosServlet");

                } catch (Exception e) {
                    e.printStackTrace();
                }
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