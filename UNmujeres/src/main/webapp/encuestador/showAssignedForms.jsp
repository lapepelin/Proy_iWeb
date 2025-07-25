<%--
  Created by IntelliJ IDEA.
  User: Luis
  Date: 13/05/2025
  Time: 00:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%
    ArrayList<Map<String, Object>> datos = (ArrayList<Map<String, Object>>) request.getAttribute("datos");
%>

<html>
<jsp:include page="../header.jsp" />
<body id="page-top">

    <!-- Page Wrapper -->
    <div id="wrapper">

        <!-- Sidebar -->
        <jsp:include page="../sidebarEnc.jsp" />
        <!-- End of Sidebar -->

        <!-- Content Wrapper -->
        <div id="content-wrapper" class="d-flex flex-column">

            <!-- Main Content -->
            <div id="content">

                <!-- Topbar -->
                <jsp:include page="../topbarEnc.jsp" />
                <!-- End of Topbar -->

                <!-- Begin Page Content -->
                <div class="container-fluid">

                    <hr/>

                    <% if (session.getAttribute("error") != null) { %>
                    <div>
                        <div class="alert alert-danger" role="alert"><%=session.getAttribute("error")%>
                        </div>
                    </div>
                    <% session.removeAttribute("error"); %>
                    <% } %>

                    <% if (session.getAttribute("success") != null) { %>
                    <div>
                        <div class="alert alert-success" role="alert"><%=session.getAttribute("success")%>
                        </div>
                    </div>
                    <% session.removeAttribute("success"); %>
                    <% } %>

                    </hr>

                    <!-- Page Heading -->
                    <h1 class="h3 mb-2 text-gray-800">Formularios Asignados</h1>
                    <p class="mb-4">Lista de formularios asignados a ti por tu Coordinador Interno.</p>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary"></h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="tablaAsig" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
<%--                                        <th >ID de formulario</th>--%>
                                        <th>Nombre de Formulario</th>
                                        <th>Respuestas registradas</th>
                                        <th>Respuestas esperadas</th>
                                        <th>Fecha de asignación</th>
                                        <th>Fecha límite</th>
                                        <th>Acciones</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <%
                                        if (datos != null && !datos.isEmpty()) {
                                            for (Map<String, Object> item: datos) {
                                    %>
                                    <tr>
<%--                                        <td style="width: 100px;"><%= item.get("id_formulario") %></td>--%>
                                        <td><%= item.get("nombre_formulario") %></td>
                                        <td><%= item.get("registros_completados") %></td>
                                        <td><%= item.get("reg_esperados_enc") %></td>
                                        <td style="white-space: nowrap;">
                                            <%= new java.text.SimpleDateFormat("dd-MM-yyyy")
                                                    .format((java.util.Date) item.get("fecha_asignacion")) %>
                                        </td>
                                        <td style="white-space: nowrap;">
                                            <%= new java.text.SimpleDateFormat("dd-MM-yyyy")
                                                    .format((java.util.Date) item.get("fecha_limite")) %>
                                        </td>

                                        <td><a class="btn btn-success" href="<%=request.getContextPath()%>/encuestador/ServletA?action=guardar&id_asig=<%= item.get("id_asignacion") %>&id_form=<%= item.get("id_formulario") %>">Crear Registro</a></td>


                                    </tr>
                                    <%
                                            }

                                    } else {
                                    %>
                                    <tr>
                                        <td colspan="6">No hay formularios asignados</td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                </div>
                <!-- /.container-fluid -->

            </div>
            <!-- End of Main Content -->

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
    <jsp:include page="../footer.jsp" />
</body>
<%--<script>--%>
<%--    // Inicializa DataTables. Si 'datatables-demo.js' ya lo hace, puedes ajustar o quitar esta parte.--%>
<%--    if ($.fn.DataTable.isDataTable('#dataTable')) {--%>
<%--        $('#dataTable').DataTable().destroy(); // Destruye cualquier instancia existente--%>
<%--    }--%>
<%--    $('#dataTable').DataTable({--%>
<%--        "order": [], // Deshabilita el orden inicial si lo deseas, o ajusta según necesites--%>
<%--        "pageLength": 10, // Número de filas por página por defecto--%>
<%--        "language": { // Configuración del idioma--%>
<%--            "url": "//cdn.datatables.net/plug-ins/1.13.4/i18n/es-ES.json" // URL para español--%>
<%--        }--%>
<%--    });--%>
<%--</script>--%>
</html>