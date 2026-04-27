package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.ProyectoComando;
import co.edu.uco.sibe.aplicacion.comando.ProyectoModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.manejador.GuardarProyectoManejador;
import co.edu.uco.sibe.aplicacion.comando.manejador.ModificarProyectoManejador;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_AREA_ADMIN_OR_ADMIN_CREATE_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.textoAUUID;

@RestController
@AllArgsConstructor
@RequestMapping(PROYECTOS)
public class ProyectoComandoControlador {
    private final GuardarProyectoManejador guardarProyectoManejador;
    private final ModificarProyectoManejador modificarProyectoManejador;

    @PreAuthorize(HAS_AREA_ADMIN_OR_ADMIN_CREATE_AUTHORITY)
    @PostMapping
    public ComandoRespuesta<UUID> guardar(@RequestBody ProyectoComando proyecto){
        return this.guardarProyectoManejador.ejecutar(proyecto);
    }

    @PreAuthorize(HAS_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY)
    @PutMapping(IDENTIFICADOR_PATH)
    public ComandoRespuesta<UUID> modificar(@RequestBody ProyectoModificacionComando proyecto, @PathVariable String identificador){
        return this.modificarProyectoManejador.ejecutar(proyecto, textoAUUID(identificador));
    }
}