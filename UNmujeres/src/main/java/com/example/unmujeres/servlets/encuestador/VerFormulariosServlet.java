package com.example.unmujeres.servlets.encuestador;
import com.example.unmujeres.beans.OpcionPregunta;
import com.example.unmujeres.beans.Usuario;
import com.example.unmujeres.daos.OpcionPreguntaDAO;
import com.example.unmujeres.daos.PreguntaDAO;
import com.example.unmujeres.daos.RegistroRespuestasDAO;


import com.example.unmujeres.beans.*;
import com.example.unmujeres.daos.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@WebServlet(
        name = "VerFormulariosServlet",
        value = {"/shared/VerFormulariosServlet", "/encuestador/ServletA", "/coordinador/VerFormulariosServlet", "/encuestador/VerFormulariosServlet"}, // Múltiples rutas de acceso
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
    PreguntaDAO preguntaDAO = new PreguntaDAO();
    OpcionPreguntaDAO opcionDAO = new OpcionPreguntaDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        Usuario user = (Usuario) session.getAttribute("usuario");
        int idUser = user.getIdUsuario();
        int userRole = user.getIdroles();

        ArrayList<EncHasFormulario> asignaciones = ehfDAO.getByUser(idUser);

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;

        switch (action) {
            case "lista":
                try {
                    System.out.println("Se consulto lista de asignados");

                    // 2. Inicializa arreglo de datos para vista
                    ArrayList<Map<String, Object>> datos = new ArrayList<>();

                    List<Integer> IDsAsignaciones = new ArrayList<>();
                    //  para cada asignacion
                    for (EncHasFormulario asignacion : asignaciones) {
                        IDsAsignaciones.add(asignacion.getIdEncHasFormulario());
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
                            item.put("id_asignacion", asignacion.getIdEncHasFormulario());
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
                    view = request.getRequestDispatcher("/encuestador/showAssignedForms.jsp");
                    view.forward(request, response);

                } catch (Exception e) {
                    response.sendRedirect(getRedirectUrl(userRole));
                }
                break;
            case "guardar":
                System.out.println("Se crea nueva respuesta");

                String idFormParam1 = request.getParameter("id_form");

                int idFormulario;
                if (idFormParam1 != null) {
                    try {
                        idFormulario = Integer.parseUnsignedInt(idFormParam1);

                        ArrayList<Integer> idsFormAsig = new ArrayList<>();
                        for (EncHasFormulario asignacion : asignaciones) {
                            idsFormAsig.add(asignacion.getFormulario().getIdFormulario());
                        }
                        if (!idsFormAsig.contains(idFormulario)) {
                            throw new IllegalArgumentException("No puede crear registros de formularios no asignados");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Parámetro de form inválido");
                        session.setAttribute("error", "Parámetro de formulario inválido");
                        response.sendRedirect(getRedirectUrl(userRole));
                        return;

                    } catch (IllegalArgumentException e) {
                    session.setAttribute("error", e.getMessage());
                    response.sendRedirect(getRedirectUrl(userRole));
                    return;
                    }
                } else {
                    session.setAttribute("error", "El parámetro de formulario no puede ser nulo");
                    response.sendRedirect(getRedirectUrl(userRole));
                    return;
                }

                // Convertir a estructura para la vista
                ArrayList<Pregunta> preguntas = preguntaDAO.getPreguntasConOpcionesPorFormulario(idFormulario);
                ArrayList<OpcionPregunta> opciones1 = opcionDAO.getByForm(idFormulario);

                request.setAttribute("preguntas", preguntas);
                request.setAttribute("opciones", opciones1);
                request.setAttribute("idformulario", idFormulario);
                request.getRequestDispatcher("/encuestador/crearRespuesta.jsp").forward(request, response);

                break;

            case "historial":
                System.out.println("Se consulto historial");
                try {

                    // 2. Inicializar arreglos de datos (borradores y completados)
                    ArrayList<Map<String, Object>> datos1 = new ArrayList<>();
                    ArrayList<Map<String, Object>> datos2 = new ArrayList<>();

                    // 3. lista de borradores
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
                            if (registro != null && "B".equals(registro.getEstado()) && formulario != null && formulario.isEstado()) {

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
                            } else if (registro != null && formulario != null && formulario.isEstado() && "C".equals(registro.getEstado())) {

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
                    view = request.getRequestDispatcher("/encuestador/responsesHistory.jsp");
                    view.forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("error", "Error inesperado");
                    response.sendRedirect(getRedirectUrl(userRole));
                }
                break;

            case "editar":

                String idFormParam = request.getParameter("id_form");
                String idRegParam = request.getParameter("id");
                ArrayList<Integer> IDsRegistros = registroDAO.getIDsByUsuario(idUser);

                //validar parametros
                int idForm;
                int idReg;
                if (idFormParam != null && idRegParam != null) {
                    try {
                        idForm = Integer.parseUnsignedInt(idFormParam);
                        idReg = Integer.parseUnsignedInt(idRegParam);

                        if (!IDsRegistros.contains(idReg)) {
                            throw new IllegalArgumentException("No puede editar registros de otros usuarios");
                        }

                        ArrayList<Integer> idsFormAsig = new ArrayList<>();
                        for (EncHasFormulario asignacion : asignaciones) {
                            idsFormAsig.add(asignacion.getFormulario().getIdFormulario());
                            if (asignacion.getIdEncHasFormulario() == idForm) {
                                idForm = asignacion.getFormulario().getIdFormulario();
                            }
                        }
                        if (!idsFormAsig.contains(idForm)) {
                            throw new IllegalArgumentException("No puede editar registros de formularios no asignados");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Parámetros de form o registro inválidos");
                        session.setAttribute("error", "Parámetros de form o registro inválidos");
                        response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                        return;
                    } catch (IllegalArgumentException e) {
                    session.setAttribute("error", e.getMessage());
                    response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                    return;
                    }
                } else {
                    session.setAttribute("error", "Los parámetros de formulario o registro no pueden ser nulos");
                    response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                    return;
                }


                System.out.println("Se consulto el editor del registro id: " + idReg+" para form "+idForm);

                RegistroRespuestas registro = registroDAO.getById(idReg);

                if (registro == null) {
                    session.setAttribute("error", "El registro no existe");
                    response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");

                } else {
                    request.setAttribute("registro", registro);

                    ArrayList<OpcionPregunta> opciones = opcionDAO.getByForm(idForm);
                    System.out.println("opciones: "+opciones);
                    request.setAttribute("opciones", opciones);
                    for (OpcionPregunta opcion : opciones) {System.out.println("Opcion es: "+opcion.getIdOpcionPregunta()+" con idpregunta: "+opcion.getIdOpcionPregunta());}

                    ArrayList<Respuesta> respuestas = respuestaDAO.listaRespuestas(idReg);
                    request.setAttribute("respuestas", respuestas);
                    view = request.getRequestDispatcher("/encuestador/editResponse.jsp");
                    view.forward(request, response);
                }
                break;

            case "descartar":
                String idRegParam1 = request.getParameter("id");
                ArrayList<Integer> IDsRegistros1 = registroDAO.getIDsByUsuario(idUser);

                //validar parametro
                int IdReg1;
                if (idRegParam1 != null) {
                    try {
                        IdReg1 = Integer.parseUnsignedInt(idRegParam1);

                        if (!IDsRegistros1.contains(IdReg1)) {
                            throw new IllegalArgumentException("No puede descartar registros de otros usuarios");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Parámetro de registro inválidos");
                        session.setAttribute("error", "Parámetros de form o registro inválidos");
                        response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                        return;

                    } catch (IllegalArgumentException e) {
                        session.setAttribute("error", e.getMessage());
                        response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                        return;
                    }
                } else {
                    session.setAttribute("error", "El parámetro de registro no puede ser nulo");
                    response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                    return;
                }

                try {

                    if(registroDAO.getById(IdReg1) != null){
                        System.out.println("Se eliminara el registro id: " + IdReg1);
                        registroDAO.delete(IdReg1);
                    }
                    session.setAttribute("success", "Registro descartado con éxito");
                    response.sendRedirect(request.getContextPath()+"/encuestador/VerFormulariosServlet?action=historial");

                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("error", "Error inesperado");
                    response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                }
                break;
        }
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        Usuario user = (Usuario) session.getAttribute("usuario");
        int idUser = user.getIdUsuario();
        int userRole = user.getIdroles();

        ArrayList<EncHasFormulario> asignaciones = ehfDAO.getByUser(idUser);

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        System.out.println("accion de dopost es: " + action);
        RequestDispatcher view;

        switch (action) {

            case "editar":
                String acto1 = request.getParameter("acto");

                String nuevoEstado1;
                if (Objects.equals(acto1, "borrador")) {
                    nuevoEstado1 = "B";
                } else if (Objects.equals(acto1, "completado")) {
                    nuevoEstado1 = "C";
                } else {
                    // Valor por defecto o generar error
                    session.setAttribute("error", "No es un acto válido");
                    response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                    return;
                }

                String idRegParam = request.getParameter("id");
                ArrayList<Integer> IDsRegistros = registroDAO.getIDsByUsuario(idUser);
                //validar parametro
                int idReg;
                if (idRegParam != null) {
                    try {
                        idReg = Integer.parseUnsignedInt(idRegParam);

                        if (!IDsRegistros.contains(idReg)) {
                            throw new IllegalArgumentException("No puede editar registros de otros usuarios");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Parámetro de registro inválidos");
                        session.setAttribute("error", "Parámetros de form o registro inválidos");
                        response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                        return;

                    } catch (IllegalArgumentException e) {
                        session.setAttribute("error", e.getMessage());
                        response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                        return;
                    }
                } else {
                    session.setAttribute("error", "El parámetro de registro no puede ser nulo");
                    response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                    return;
                }

                try {
                    registroDAO.updateState(idReg,nuevoEstado1);
                    System.out.println("Se ha actualizado el registro con id: " + idReg + "al estado: " + nuevoEstado1 + " en" + nuevoEstado1.getClass().getSimpleName());

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
                } catch (Exception e) {
                    session.setAttribute("error", "Error inesperado al editar registro");
                    response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");
                }
                session.setAttribute("success", "Registro actualizado con éxito");
                response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=historial");

                break;

            case "guardar":
                
                String acto = request.getParameter("acto");

                String nuevoEstado = "B";
                if (Objects.equals(acto, "borrador")) {
                    nuevoEstado = "B";
                } else if (Objects.equals(acto, "completado")) {
                    nuevoEstado = "C";
                } else {
                    session.setAttribute("error", "No es un acto válido");
                    response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet");
                    return;
                }
                System.out.println("se hace para: " + nuevoEstado);

                String idEncHasFormularioParam = request.getParameter("idasignacion");
                int idEncHasFormulario;
                int idForm=0;

                if (idEncHasFormularioParam != null) {
                    try {
                        // Se usa parseUnsignedInt para validar el parámetro
                        idEncHasFormulario = Integer.parseUnsignedInt(idEncHasFormularioParam);



                        ArrayList<Integer> idsAsignaciones = new ArrayList<>();
                        for (EncHasFormulario asignacion : asignaciones) {
                            idsAsignaciones.add(asignacion.getIdEncHasFormulario());
                            if (asignacion.getIdEncHasFormulario() == idEncHasFormulario) {
                                idForm = asignacion.getFormulario().getIdFormulario();
                            }
                        }

                        if (idsAsignaciones.contains(idEncHasFormulario)) {

                            System.out.println("id form es " + idForm);

                            // Se recuperan las preguntas y opciones según el formulario
                            ArrayList<Pregunta> preguntas = preguntaDAO.getPreguntasConOpcionesPorFormulario(idForm);
                            ArrayList<OpcionPregunta> opciones = opcionDAO.getByForm(idForm);

                            session.setAttribute("preguntas", preguntas);
                            session.setAttribute("opciones", opciones);
                        } else {
                            throw new IllegalArgumentException("No puede generar respuestas para formularios no asignados");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Parámetros de form o asignacion inválidos");
                        session.setAttribute("error", "Parámetro de asignación inválido");
                        response.sendRedirect(getRedirectUrl(userRole));
                        return;
                    } catch (IllegalArgumentException e) {
                        session.setAttribute("error", e.getMessage());
                        response.sendRedirect(getRedirectUrl(userRole));
                        return;
                    }
                } else {
                    session.setAttribute("error", "El parámetro de asignación no puede ser nulo");
                    response.sendRedirect(getRedirectUrl(userRole));
                    return;
                }

                try {
                    System.out.println("El id de asignación es: " + idEncHasFormulario);
                    EncHasFormulario ehf = ehfDAO.getById(idEncHasFormulario);

                    // 1. Preparar un mapa de preguntas según el id
                    List<Pregunta> preguntas = (List<Pregunta>) session.getAttribute("preguntas");
                    Map<Integer, Pregunta> preguntasMap = new HashMap<>();
                    for (Pregunta pregunta : preguntas) {
                        preguntasMap.put(pregunta.getIdPregunta(), pregunta);
                    }

                    // 2. Validar parámetros antes de crear el registro
                    Map<Integer, String> errores = new HashMap<>();
                    Map<Integer, String> inputs = new HashMap<>();
                    Map<String, String[]> parametros = request.getParameterMap();

                    for (String paramName : parametros.keySet()) {
//                        System.out.println("PARAM MAP:");
//                        System.out.println("key  "+paramName+ ". Valor "+ Arrays.toString(parametros.get(paramName)));

                        if (paramName.startsWith("pregunta_")) {
                            String[] parts = paramName.split("_");
                            int idPregunta = Integer.parseInt(parts[1]);


                            try {
                                inputs.put(idPregunta, parametros.get(paramName)[0]);
                            } catch (Exception e) {
                                System.out.println("ERROR DE MAPEO para Inputs: " + e.getMessage());
                                throw new RuntimeException(e);
                            }


                            Pregunta pregunta = preguntasMap.get(idPregunta);

                            if (pregunta != null) {
                                // Para checkbox (múltiples valores) se espera que venga un sufijo adicional en el nombre
                                if (parts.length > 2) {
                                    String[] valores = request.getParameterValues(paramName);
                                    // Validar que, si la pregunta es requerida, haya al menos una opción seleccionada.
                                    if (valores == null || valores.length == 0) {
                                        errores.put(idPregunta, "Debe seleccionar al menos una opción");
                                    }
                                } else {
                                    String valor = request.getParameter(paramName);
                                    //Validamos diferente para marcar registro en borrador o completado
                                    if ("B".equals(nuevoEstado)) {
                                        if (valor != null && !valor.trim().isEmpty()) {
                                            // Solo validamos si se ingresó algún valor
                                            String errorMsg = validarPregunta(pregunta, valor);
                                            if (errorMsg != null) {
                                                errores.put(idPregunta, errorMsg);
                                            }
                                        }
                                    } else {
                                        String errorMsg = validarPregunta(pregunta, valor);
                                        if (errorMsg != null) {
                                            errores.put(idPregunta, errorMsg);}
                                    }
                                }
                            }
                        }
                    }

                    // 3. Si hay errores de validación, reenvía al formulario para que el usuario corrija los datos.
                    if (!errores.isEmpty()) {
                        //request.setAttribute("error", errores);
                        session.setAttribute("validationErrors", errores);
                        session.setAttribute("valoresFormulario", inputs);
                        response.sendRedirect(request.getContextPath() + "/encuestador/VerFormulariosServlet?action=guardar&id_form="+idForm+"&id_asig="+idEncHasFormulario);
                        return;
                    }

                    // 4. Si la validación fue exitosa, crear el registro principal.
                    RegistroRespuestas nuevoRegistro = new RegistroRespuestas();
                    nuevoRegistro.setFechaRegistro(new Date(System.currentTimeMillis()));
                    nuevoRegistro.setEstado(nuevoEstado);
                    nuevoRegistro.setEncHasFormulario(ehf);
                    int idRegistro = registroDAO.crearRegistroRespuestas(nuevoRegistro);
                    System.out.println("Nuevo Registro id es: " + idRegistro);

                    // 5. Procesar las respuestas ya validadas
                    Map<Integer, String> respuestasTexto = new HashMap<>();
                    Map<Integer, List<Integer>> respuestasOpciones = new HashMap<>();

                    for (String paramName : parametros.keySet()) {
                        if (paramName.startsWith("pregunta_")) {
                            String[] parts = paramName.split("_");
                            int idPregunta = Integer.parseInt(parts[1]);
                            Pregunta pregunta = preguntasMap.get(idPregunta);

                            if (pregunta != null) {
                                // Si es checkbox (múltiples valores)
                                if (parts.length > 2) {
                                    String[] valores = request.getParameterValues(paramName);
                                    if (valores != null) {
                                        List<Integer> opciones = new ArrayList<>();
                                        for (String valor : valores) {
                                            // Asumimos que el valor es numérico (id de opción)
                                            opciones.add(Integer.parseInt(valor));
                                        }
                                        respuestasOpciones.put(idPregunta, opciones);
                                    }
                                } else {
                                    String valor = request.getParameter(paramName);
                                    if (valor != null && !valor.trim().isEmpty()) {
                                        respuestasTexto.put(idPregunta, valor.trim());
                                    } else {
                                        respuestasTexto.put(idPregunta, null);
                                    }
                                }
                            }
                        }
                    }

                    // 6. Guardar las respuestas según se hayan recibido
                    if (!respuestasTexto.isEmpty()) {
                        respuestaDAO.guardarRespuestas(idRegistro, respuestasTexto);
                    }
                    if (!respuestasOpciones.isEmpty()) {
                        respuestaDAO.guardarRespuestasOpciones(idRegistro, respuestasOpciones);
                    }

                    session.setAttribute("success", "Registro creado con exito y guardado como "+acto);
                    response.sendRedirect(getRedirectUrl(userRole));

                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("error", "Error al guardar las respuestas");
                    response.sendRedirect(getRedirectUrl(userRole));

                }

            break;

        }
    }


    // MÉTODO PRIVADO PARA VALIDAR EL VALOR DE UNA PREGUNTA
    private String validarPregunta(Pregunta pregunta, String valor) {
        // y, opcionalmente, isRequerida() y getOpciones() (para combobox)
        String tipo = pregunta.getTipoDato();

        // Si el campo es requerido y el valor es nulo o vacío, se retorna un error.
        if (pregunta.getRequerido() && (valor == null || valor.trim().isEmpty())) {
            return "El campo es obligatorio.";
        }

        // Si el valor no es obligatorio y está vacío, se acepta (retornamos null, sin error)
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }

        valor = valor.trim();

        if ("int".equalsIgnoreCase(tipo) || "number".equalsIgnoreCase(tipo)) {
            try {
                Integer.parseInt(valor);
            } catch (NumberFormatException e) {
                return "Debe ingresar un número válido.";
            }
        } else if ("date".equalsIgnoreCase(tipo)) {
            try {
                System.out.println("Date: " + valor);
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate.parse(valor.trim(), sqlFormatter);


            } catch (DateTimeParseException e) {
                return "Introduzca una fecha válida (dd-mm-yyyy).";
            }
        } else if ("select".equalsIgnoreCase(tipo) || "combobox".equalsIgnoreCase(tipo)) {
            // Se asume que getOpciones() devuelve una lista de opciones válidas (por ejemplo, List<String>)
            List<String> opcionesValidas = opcionDAO.getByPreguntaToString(pregunta.getIdPregunta());
            if (opcionesValidas != null && !opcionesValidas.contains(valor)) {
                return "La opción seleccionada no es válida.";
            }
        }

        // Si no se encontró ningún error, se retorna null.
        return null;
    }

    private String getRedirectUrl(int userRole) {
        if (userRole == 3) {
            return (getServletContext().getContextPath() + "/encuestador/VerFormulariosServlet");
        } else if (userRole == 2) {
            return (getServletContext().getContextPath() + "/coordinador/SubirRegistrosServlet");
        } else {
            // Valor por defecto en caso de otro rol
            return (getServletContext().getContextPath() + "/access-denied.jsp");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Ejemplo básico para otros métodos HTTP
        response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}

