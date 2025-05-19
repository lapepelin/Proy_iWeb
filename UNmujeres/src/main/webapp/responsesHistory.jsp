<%--
  Created by IntelliJ IDEA.
  User: Luis
  Date: 13/05/2025
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Map" %>
<%
  ArrayList<Map<String, Object>> drafts = (ArrayList<Map<String, Object>>) request.getAttribute("drafts");
%>
<%
  ArrayList<Map<String, Object>> records = (ArrayList<Map<String, Object>>) request.getAttribute("records");
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
          <h1 class="h3 mb-2 text-gray-800">Registros históricos</h1>
          <p class="mb-4">Las respuestas guardadas en borrador o completadas se muestran aquí.</p>

          <!-- Sección Borradores -->
          <div class="card shadow mb-4" id="borradoresSection">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-primary">Respuestas en Borrador</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="tablaDrafts" width="100%" cellspacing="0">
                  <thead>
                  <tr>
                    <th># de registro</th>
                    <th >ID de formulario</th>
                    <th>Nombre</th>
                    <th>Fecha de creación</th>
                    <th>Fecha límite</th>
                    <th>Acciones</th>
                  </tr>
                  </thead>
                  <tbody>
                  <%
                    if (drafts != null && !drafts.isEmpty()) {
                      for (Map<String, Object> item: drafts) {
                  %>
                  <tr>
                    <td style="width: 90px;"><%= item.get("id_registro") %></td>
                    <td><%= item.get("id_formulario") %></td>
                    <td><%= item.get("nombre_formulario") %></td>
                    <td>
                      <%= new java.text.SimpleDateFormat("dd-MM-yyyy")
                              .format((java.util.Date) item.get("fecha_registro")) %>
                    </td>
                    <td>
                      <%= new java.text.SimpleDateFormat("dd-MM-yyyy")
                              .format((java.util.Date) item.get("fecha_limite")) %>
                    </td>
                    <td>
                      <a class="btn btn-sm btn-info" href="<%=request.getContextPath()%>/ServletA?action=editar&id=<%=item.get("id_registro")%>">Editar</a>
                      <a class="btn btn-sm btn-warning" href="<%=request.getContextPath()%>/ServletA?action=descartar&id=<%=item.get("id_registro")%>">Descartar</a>
                    </td>
                  </tr>
                  <%
                    }

                  } else {
                  %>
                  <tr>
                    <td colspan="5">No hay registros en borrador</td>
                  </tr>
                  <%
                    }
                  %>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <!-- Sección Completados -->
          <div class="card shadow mb-4" id="completadosSection">
            <div class="card-header py-3">
              <h6 class="m-0 font-weight-bold text-success">Respuestas Completadas</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="tablaRecords" width="100%" cellspacing="0">
                  <thead>
                  <tr>
                    <th># de registro</th>
                    <th>ID de formulario</th>
                    <th>Nombre de formulario</th>
                    <th>Fecha de registro</th>
                  </tr>
                  </thead>
                  <tbody>
                  <%
                    if (records != null && !records.isEmpty()) {
                      for (Map<String, Object> item: records) {
                  %>
                  <tr>
                    <td style="width: 90px;"><%= item.get("id_registro") %></td>
                    <td><%= item.get("id_formulario") %></td>
                    <td><%= item.get("nombre_formulario") %></td>
                    <td>
                      <%= new java.text.SimpleDateFormat("dd-MM-yyyy")
                              .format((java.util.Date) item.get("fecha_registro")) %>
                    </td>
                  </tr>
                  <%
                    }

                  } else {
                  %>
                  <tr>
                    <td colspan="4">No hay registros completados</td>
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
