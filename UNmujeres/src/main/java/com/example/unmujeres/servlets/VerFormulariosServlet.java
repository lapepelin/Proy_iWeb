package com.example.unmujeres.servlets;

import com.example.unmujeres.beans.*;
import com.example.unmujeres.daos.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

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

    EncHasFormularioDAO ehfDAO = new EncHasFormularioDAO();
    RegistroRespuestasDAO registroDAO = new RegistroRespuestasDAO();
    FormularioDAO formularioDAO = new FormularioDAO();
    RespuestaDAO respuestaDAO = new RespuestaDAO();
    OpcionPreguntaDAO opcionDAO = new OpcionPreguntaDAO();
    int idEnc = 7;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
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
                try {

                    // 2. Inicializar arreglos de datos (borradores y completados)
                    ArrayList<Map<String, Object>> datos1 = new ArrayList<>();
                    ArrayList<Map<String, Object>> datos2 = new ArrayList<>();

                    // 3. lista de borradores
                    ArrayList<EncHasFormulario> asignaciones = ehfDAO.getByEncuestador(idEnc); // ID hardcodeado
                    for (EncHasFormulario asignacion : asignaciones) {
                        System.out.println("Asignacion id es: "+asignacion.getIdEncHasFormulario());

                        // 3.1. Informacion de registro, arreglo de registros en asignacion
                        ArrayList<RegistroRespuestas> registros = registroDAO.getByEhf(asignacion.getIdEncHasFormulario());
                        // 3.2. Obtener formulario relacionado por el id, obtenido de la asignacion
                        Formulario formulario = formularioDAO.getById(asignacion.getFormulario().getIdFormulario());
                        System.out.println("Form id es: "+formulario.getIdFormulario());

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

                                Date fecha = registro.getFechaRegistro();

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
                    view = request.getRequestDispatcher("/responsesHistory.jsp");
                    view.forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "editar":
                int idReg = Integer.parseInt(request.getParameter("id"));
                System.out.println("Se consulto el editor del registro con id: " + idReg);

                RegistroRespuestas registro = registroDAO.getById(idReg);

                if (registro == null) {
                    response.sendRedirect(request.getContextPath() + "/VerFormulariosServlet");
                } else {
                    request.setAttribute("registro", registro);

                    ArrayList<OpcionPregunta> opciones = opcionDAO.getByReg(idReg);
                    request.setAttribute("opciones", opciones);

                    ArrayList<Respuesta> respuestas = respuestaDAO.listaRespuestas(idReg);
                    request.setAttribute("respuestas", respuestas);
                    view = request.getRequestDispatcher("/editResponse.jsp");
                    view.forward(request, response);
                }
                break;

            case "descartar":
                int IdReg = Integer.parseInt(request.getParameter("id"));

                try {

                    if(registroDAO.getById(IdReg) != null){
                        System.out.println("Se eliminara el registro id: " + IdReg);
                        registroDAO.delete(IdReg);
                    }
                    response.sendRedirect(request.getContextPath()+"/VerFormulariosServlet?action=historial");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        System.out.println("se hace: " +action);
        RequestDispatcher view;

        switch (action) {
            case "crearReg":

                break;
            case "editar":
                String acto = request.getParameter("acto");

                String nuevoEstado;
                if (Objects.equals(acto, "borrador")) {
                    nuevoEstado = "B";
                } else if (Objects.equals(acto, "completado")) {
                    nuevoEstado = "C";
                } else {
                    // Valor por defecto o generar error
                    nuevoEstado = "B";
                }

                int idReg = Integer.parseInt(request.getParameter("idregistro_respuestas"));
                registroDAO.updateState(idReg,nuevoEstado);
                System.out.println("Se ha actualizado el registro con id: " + idReg + "al estado: " + nuevoEstado + " en" + nuevoEstado.getClass().getSimpleName());

                Map<String, String[]> parametroMap = request.getParameterMap();
                // Itera sobre los parámetros para identificar elementos que comiencen con "respuesta_"
                for (String paramName : parametroMap.keySet()) {
                    if (paramName.startsWith("respuesta_")) {
                        // Extrae el id de la pregunta del nombre del input (por ejemplo, "respuesta_45")
                        String idStr = paramName.substring("respuesta_".length());
                        try {
                            int idPregunta = Integer.parseInt(idStr);
                            // Obtiene el valor del input
                            String nuevaRespuesta = request.getParameter(paramName);
                            if (nuevaRespuesta != null) {
                                nuevaRespuesta = nuevaRespuesta.trim();
                            }
                            // Actualiza la respuesta para esa pregunta en el registro de respuestas
                            respuestaDAO.updateResponse(idReg, idPregunta, nuevaRespuesta);

                            System.out.println("Se actualizo una respuesta con id: " + idPregunta + "con el texto: " + nuevaRespuesta);

                        } catch (NumberFormatException e) {
                            System.err.println("ID de pregunta inválido en el parámetro: " + paramName);
                        }
                    }
                }

                response.sendRedirect(request.getContextPath() + "/VerFormulariosServlet?action=historial");

                break;
        }





    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ejemplo básico para otros métodos HTTP
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}