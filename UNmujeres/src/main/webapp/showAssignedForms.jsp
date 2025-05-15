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
<%@ page import="com.example.unmujeres.beans.Formulario" %>
<%
    ArrayList<Map<String, Object>> datos = (ArrayList<Map<String, Object>>) request.getAttribute("datos");
%>

<html>

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
                    <h1 class="h3 mb-2 text-gray-800">Formularios Asignados</h1>
                    <p class="mb-4">Lista de formularios asignados a ti por tu Coordinador Interno.</p>

                    <!-- DataTales Example -->
                    <div class="card shadow mb-4">
                        <div class="card-header py-3">
                            <h6 class="m-0 font-weight-bold text-primary"></h6>
                        </div>
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>ID de formulario</th>
                                        <th>Nombre</th>
                                        <th>Respuestas registradas</th>
                                        <th>Respuestas esperadas</th>
                                        <th>Fecha de asignación (YYYY-MM-DD)</th>
                                        <th>Fecha límite (YYYY-MM-DD)</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <%
                                        if (datos != null && !datos.isEmpty()) {
                                            for (Map<String, Object> item: datos) {
                                    %>
                                    <tr>
                                        <td><%= item.get("id_formulario") %></td>
                                        <td><%= item.get("nombre_formulario") %></td>
                                        <td><%= item.get("registros_comletados") %></td>
                                        <td><%= item.get("registros_esperados") %></td>
                                        <td><%= item.get("fecha_asignacion") %></td>
                                        <td><%= item.get("fecha_limite") %></td>

                                        <td><a href="<%=request.getContextPath()%>/ServletA?action=regCrear&id=<%= item.get("id_formulario") %>">Crear Registro</a></td>
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

<jsp:include page="footer.jsp" />
</body>
</html>
