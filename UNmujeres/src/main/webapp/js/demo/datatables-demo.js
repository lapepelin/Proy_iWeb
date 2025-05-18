// Call the dataTables jQuery plugin
$(document).ready(function() {
  $('#dataTable').DataTable();
});

$(document).ready(function() {
    $('#tablaAsig').DataTable({
        //paging: true,       // Habilita la paginación
        //ordering: true,     // Permite el ordenamiento por columnas
        //searching: true,    // Agrega la funcionalidad de búsqueda global
        //info: true,         // Muestra información de la tabla (por ejemplo, "Mostrando X de Y entradas")
    });
});

$(document).ready(function() {
    $('#tablaDrafts').DataTable();
});

$(document).ready(function() {
    $('#tablaRecords').DataTable();
});