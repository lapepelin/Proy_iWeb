<%--
  Created by IntelliJ IDEA.
  User: Luis
  Date: 13/05/2025
  Time: 13:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

  <jsp:include page="header.jsp" />

  <body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

      <!-- Sidebar - Brand -->
      <a class="sidebar-brand d-flex align-items-center justify-content-center" href="indexEnc.html">
        <div class="sidebar-brand-icon">
          <img src="img/ONU.png" alt="Logo ONU Mujeres" style="max-width: 100%;">
        </div>

      </a>

      <!-- Divider -->
      <hr class="sidebar-divider my-0">

      <!-- Nav Item - Dashboard -->
      <li class="nav-item">
        <a class="nav-link" href="indexEnc.html">
          <i class="fas fa-fw fa-tachometer-alt"></i>
          <span>Resumen</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Funciones
      </div>

      <!-- Nav Item - Nueva Respuesta -->
      <li class="nav-item">
        <a class="nav-link" href="crearRespuesta.html">
          <i class="fas fa-fw fa-clipboard-list"></i>
          <span>Crear Nueva Respuesta</span></a>
      </li>

      <!-- Nav Item - Ver Forms Asignados -->
      <li class="nav-item">
        <a class="nav-link" href="asignForm.html">
          <i class="fas fa-fw fa-table"></i>
          <span>Formularios Asignados</span></a>
      </li>

      <!-- Historical Collapse Menu -->
      <li class="nav-item active">
        <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseUtilities"
           aria-expanded="true" aria-controls="collapseUtilities">
          <i class="fas fa-fw fa-clock"></i>
          <span>Historial de Formularios</span>
        </a>
        <div id="collapseUtilities" class="collapse" aria-labelledby="headingUtilities"
             data-parent="#accordionSidebar">
          <div class="bg-white py-2 collapse-inner rounded">
            <h6 class="collapse-header">Seleccione:</h6>
            <a class="collapse-item" href="historicForms.html#completadosSection">Completados</a>
            <a class="collapse-item" href="historicForms.html#borradoresSection">Borradores</a>
          </div>
        </div>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider">

      <!-- Heading -->
      <div class="sidebar-heading">
        Personal
      </div>

      <!-- Nav Item - Tables -->
      <li class="nav-item">
        <a class="nav-link" href="perfilEnc.html">
          <i class="fas fa-fw fa-user"></i>
          <span>Perfil</span></a>
      </li>

      <!-- Divider -->
      <hr class="sidebar-divider d-none d-md-block">

      <!-- Sidebar Toggler (Sidebar) -->
      <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
      </div>

      <!-- Sidebar Message -->
      <div class="sidebar-card d-none d-lg-flex">
        <p class="text-center mb-2"><strong>ONU</strong> Municipalidad distrital de Villa el Salvador</p>
        <a class="btn btn-success btn-sm mb-2" href="https://www.unwomen.org/es">Contacto</a>
        <a class="btn btn-danger btn-sm" href="#" data-toggle="modal" data-target="#logoutModal">Cerrar sesión</a>

      </div>



    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

      <!-- Main Content -->
      <div id="content">

        <!-- Topbar -->
        <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

          <!-- Sidebar Toggle (Topbar) -->
          <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
            <i class="fa fa-bars"></i>
          </button>

          <!-- Topbar Navbar -->
          <ul class="navbar-nav ml-auto">


            <div class="topbar-divider d-none d-sm-block"></div>

            <!-- Nav Item - User Information -->
            <li class="nav-item dropdown no-arrow">
              <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                 data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                <span class="mr-2 d-none d-lg-inline text-gray-600 small">Nombre prueba</span>
                <img class="img-profile rounded-circle"
                     src="img/undraw_profile.svg">
              </a>
              <!-- Dropdown - User Information -->
              <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                   aria-labelledby="userDropdown">
                <a class="dropdown-item" href="perfilEnc.html">
                  <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                  Perfil
                </a>

                <a class="dropdown-item" href="indexEnc.html#">
                  <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                  Actividades recientes
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                  <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                  Cerrar Sesión
                </a>
              </div>
            </li>

          </ul>

        </nav>
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
                <table class="table table-bordered" id="borradoresTable" width="100%" cellspacing="0">
                  <thead>
                  <tr>
                    <th>Distrito</th>
                    <th>Fecha límite (YYYY-MM-DD)</th>
                    <th>Fecha de registro (YYYY-MM-DD)</th>
                    <th>ID de formulario</th>
                    <th>Acciones</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td>Miraflores</td>
                    <td>2025-06-30</td>
                    <td>2025-03-01</td>
                    <td>FORM-123</td>
                    <td>
                      <button class="btn btn-sm btn-info">Editar</button>
                      <button class="btn btn-sm btn-warning">Descartar</button>
                    </td>
                  </tr>
                  <tr>
                    <td>Miraflores</td>
                    <td>2025-06-30</td>
                    <td>2025-03-08</td>
                    <td>FORM-123</td>
                    <td>
                      <button class="btn btn-sm btn-info">Editar</button>
                      <button class="btn btn-sm btn-warning">Descartar</button>
                    </td>
                  </tr>
                  <tr>
                    <td>Cieneguilla</td>
                    <td>2025-06-30</td>
                    <td>2025-04-08</td>
                    <td>FORM-123</td>
                    <td>
                      <button class="btn btn-sm btn-info">Editar</button>
                      <button class="btn btn-sm btn-warning">Descartar</button>
                    </td>
                  </tr>
                  <tr>
                    <td>Miraflores</td>
                    <td>2025-08-31</td>
                    <td>2025-03-30</td>
                    <td>FORM-456</td>
                    <td>
                      <button class="btn btn-sm btn-info">Editar</button>
                      <button class="btn btn-sm btn-warning">Descartar</button>
                    </td>
                  </tr>
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
                <table class="table table-bordered" id="completadosTable" width="100%" cellspacing="0">
                  <thead>
                  <tr>
                    <th>Distrito</th>
                    <!--<th>Zona</th>-->
                    <!--<th>ID de respuesta</th>-->
                    <th>Fecha de registro</th>
                    <th>ID de formulario</th>
                  </tr>
                  </thead>
                  <tbody>
                  <tr>
                    <td>Miraflores</td>
                    <!--<td>Este</td>-->
                    <td>2024-03-03</td>
                    <td>FORM-789</td>
                  </tr>
                  <tr>
                    <td>Cieneguilla</td>
                    <!--<td>Oeste</td>-->
                    <td>2024-03-04</td>
                    <td>FORM-012</td>
                  </tr>
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
