<%--
  Created by IntelliJ IDEA.
  User: Luis
  Date: 13/05/2025
  Time: 00:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

  <!-- Sidebar - Brand -->
  <a class="sidebar-brand d-flex align-items-center justify-content-center" href="indexEnc.jsp">
    <div class="sidebar-brand-icon">
      <img src="img/ONU.png" alt="Logo ONU Mujeres" style="max-width: 100%;">
    </div>

  </a>

  <!-- Divider -->
  <hr class="sidebar-divider my-0">

  <!-- Nav Item - Dashboard -->
  <li class="nav-item ${pageAct eq 'dashboard' ? 'active' : ''}">
    <a class="nav-link" href="showDashboard.jsp">
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
  <li class="nav-item ${pageAct eq 'respuesta' ? 'active' : ''}">
    <a class="nav-link" href="crearRespuesta.html">
      <i class="fas fa-fw fa-clipboard-list"></i>
      <span>Crear Nueva Respuesta</span></a>
  </li>

  <!-- Nav Item - Ver Forms Asignados -->
  <li class="nav-item ${pageAct eq 'asignados' ? 'active' : ''}">
    <a class="nav-link" href="showAssignedForms.jsp">
      <i class="fas fa-fw fa-table"></i>
      <span>Formularios Asignados</span></a>
  </li>

  <!-- Historial Collapse Menu -->
  <li class="nav-item ${pageAct eq 'historial' ? 'active' : ''}">
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
  <li class="nav-item ${pageAct eq 'perfil' ? 'active' : ''}">
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