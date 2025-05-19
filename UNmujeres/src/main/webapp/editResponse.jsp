<%--
  Created by IntelliJ IDEA.
  User: Luis
  Date: 18/05/2025
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.unmujeres.beans.OpcionPregunta" %>
<%@ page import="com.example.unmujeres.beans.*" %>
<%
    ArrayList<Respuesta> respuestas = (ArrayList<Respuesta>) request.getAttribute("respuestas");
    RegistroRespuestas registro = (RegistroRespuestas) request.getAttribute("registro");
    ArrayList<OpcionPregunta> opciones = (ArrayList<OpcionPregunta>) request.getAttribute("opciones");
%>

<html lang="es">
<jsp:include page="header.jsp" />
<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <jsp:include page="sidebarEnc.jsp" />
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <jsp:include page="topbarEnc.jsp" />
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="d-sm-flex align-items-center justify-content-between mb-4">
                        <h1 class="h3 mb-0 text-gray-800">Crear nueva respuesta</h1>
                    </div>

                    <%
                        if (respuestas != null && !respuestas.isEmpty()) {
                            // Variable para controlar el cambio de sección.
                            int currentSeccionId = -1;
                    %>
                        <form id="respuestaForm" method="POST" action="<%=request.getContextPath()%>/VerFormulariosServlet?action=editar">
                        <!-- Campo hidden para el id del registro de respuestas -->
                        <input type="hidden" name="idregistro_respuestas" value="<%= registro.getIdRegistroRespuestas() %>" />
                    <%
                        // Recorrer cada respuesta (la cual contiene la pregunta y sección asociada)
                        for (Respuesta respuesta : respuestas) {
                            Pregunta pregunta = respuesta.getPregunta();
                            Seccion sec = pregunta.getSeccion();
                            // Si cambia la sección (o es la primera iteración), se inicia una nueva card.
                            if (sec.getIdSeccion() != currentSeccionId) {
                                // Si no es la primera sección, se cierra la card anterior y su contenedor row.
                                if (currentSeccionId != -1) {
                    %>
                                    </div>  <!-- Cierra div.row -->
                                </div>  <!-- Cierra card-body -->
                            </div>  <!-- Cierra card -->
                            <br/>
                    <%
                                    }
                                    currentSeccionId = sec.getIdSeccion();
                    %>
                            <!-- Nueva Card para la sección -->
                            <div class="card">
                                <div class="card-header">
                                    <h5 class="card-title m-0 font-weight-bold text-primary"><%= sec.getNombreSec() %></h5>
                                </div>
                                <div class="card-body">
                                    <div class="row">
                    <%
                            }
                    %>
                                <!-- Cada pregunta se coloca en una columna de 4 (12/4 = 3 columnas) -->
                                        <div class="col-md-4">
                                            <div class="pregunta">
                                                <p><strong>Pregunta:</strong> <%= pregunta.getEnunciado() %></p>
                                                <div class="form-group">
                                                    <label for="respuesta_<%= respuesta.getIdRespuesta() %>">Respuesta:</label>
                                                    <%
                                                        if ("combobox".equalsIgnoreCase(pregunta.getTipoDato())) {

                                                    %>
                                                    <select name="respuesta_<%= respuesta.getIdRespuesta() %>"
                                                            id="respuesta_<%= respuesta.getIdRespuesta() %>"
                                                            class="form-control">
                                                        <option value="">-- Seleccione --</option>
                                                        <%
                                                            if(opciones != null) {
                                                                for(OpcionPregunta opcion : opciones) {
                                                                    if(opcion.getPregunta().getIdPregunta() == pregunta.getIdPregunta()) {
                                                                        String selected = "";
                                                                        if (respuesta.getRespuesta() != null && respuesta.getRespuesta().equals(opcion.getOpcion())) {
                                                                            selected = "selected";
                                                                        }
                                                        %>
                                                        <option value="<%= opcion.getOpcion() %>" <%= selected %>>
                                                            <%= opcion.getOpcion() %>
                                                        </option>
                                                        <%
                                                                    }
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                                    <%
                                                    } else {
                                                        String inputType = "text";
                                                        if ("int".equalsIgnoreCase(pregunta.getTipoDato())) {
                                                            inputType = "number";
                                                        } else if ("date".equalsIgnoreCase(pregunta.getTipoDato())) {
                                                            inputType = "date";
                                                        }
                                                    %>
                                                    <input type="<%= inputType %>"
                                                           class="form-control"
                                                           id="respuesta_<%= respuesta.getIdRespuesta() %>"
                                                           name="respuesta_<%= respuesta.getIdRespuesta() %>"
                                                           value="<%= (respuesta.getRespuesta() != null ? respuesta.getRespuesta() : "") %>" />
                                                    <%
                                                        }
                                                    %>
                                                </div>
                                            </div>
                                        </div>
                    <%
                        } // Fin del for.

                        // Cerrar la última card y el contenedor row si se abrió alguna
                        if (currentSeccionId != -1) {
                    %>
                                    </div>  <!-- Cierra div.row de la última sección -->
                                </div>  <!-- Cierra card-body de la última sección -->
                            </div>  <!-- Cierra última card -->
                            <br/>
                    <%
                         }
                    %>
                        </form>
                    <%
                        } else {
                    %>
                        <p>No se encontraron preguntas para mostrar.</p>
                    <%
                        }
                    %>

                </div>
            </div>

            <!-- Botones de Acción -->


<%--            <!-- Botones para guardar -->--%>
<%--            <div class="btn-group text-center mt-4">--%>
<%--                <a type="submit" name="accion" value="borrador" class="btn btn-secondary btn-icon-split">--%>
<%--                    <span class="icon text-white-50"><i class="fas fa-check"></i></span>--%>
<%--                    Guardar como Borrador</a>--%>
<%--                <a type="submit" name="accion" value="completado" class="btn btn-success btn-icon-split">--%>
<%--                    <span class="icon text-white-50"><i class="fas fa-save"></i></span>--%>
<%--                    Guardar Registro Completado</a>--%>
<%--            </div>--%>


            <div class="text-center mt-4">
                <button type="button" class="btn btn-success btn-icon-split mr-2" data-toggle="modal" data-target="#SaveRegModal">
                        <span class="icon text-white-50">
                            <i class="fas fa-check"></i>
                        </span>
                    <span class="text">Registrar Respuesta</span>
                </button>
                <button type="submit" name="acto" value="borrador" form="respuestaForm" class="btn btn-secondary btn-icon-split mr-2">
                        <span class="icon text-white-50">
                            <i class="fas fa-save"></i>
                        </span>
                    <span class="text">Guardar como Borrador</span>
                </button>
            </div>

            <!-- Footer -->
            <footer class="sticky-footer bg-white">
                <div class="container my-auto">
                    <div class="copyright text-center my-auto">
                        <span>Copyright &copy; ONU Mujeres - PUCP 2025</span>
                    </div>
                </div>
            </footer>
            <!-- End of Footer -->

        </div>
        <!-- End of Content Wrapper -->

    </div>
    <!-- End of Page Wrapper -->
    <jsp:include page="footer.jsp" />
</body>

</html>