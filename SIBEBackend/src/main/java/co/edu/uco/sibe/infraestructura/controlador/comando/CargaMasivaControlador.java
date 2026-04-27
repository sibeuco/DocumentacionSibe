package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.manejador.CargarMasivamenteEmpleadosManejador;
import co.edu.uco.sibe.aplicacion.comando.manejador.CargarMasivamenteEstudiantesManejador;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.ArchivoConstante.ARCHIVO_PARAM;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_AREA_ADMIN_OR_ADMIN_CREATE_AUTHORITY;

@RestController
@AllArgsConstructor
@RequestMapping(CARGA_MASIVA)
public class CargaMasivaControlador {
    private final CargarMasivamenteEmpleadosManejador cargarMasivamenteEmpleadosManejador;
    private final CargarMasivamenteEstudiantesManejador cargarMasivamenteEstudiantesManejador;

    @PreAuthorize(HAS_AREA_ADMIN_OR_ADMIN_CREATE_AUTHORITY)
    @PostMapping(EMPLEADOS)
    public ComandoRespuesta<List<UUID>> cargarMasivamenteEmpleados(@RequestParam(ARCHIVO_PARAM) MultipartFile archivoEmpleadoComando){
        return this.cargarMasivamenteEmpleadosManejador.ejecutar(archivoEmpleadoComando);
    }

    @PreAuthorize(HAS_AREA_ADMIN_OR_ADMIN_CREATE_AUTHORITY)
    @PostMapping(ESTUDIANTES)
    public ComandoRespuesta<List<UUID>> cargarMasivamenteEstudiantes(@RequestParam(ARCHIVO_PARAM) MultipartFile archivoEstudianteComando){
        return this.cargarMasivamenteEstudiantesManejador.ejecutar(archivoEstudianteComando);
    }
}