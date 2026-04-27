package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.ActividadComando;
import co.edu.uco.sibe.aplicacion.comando.ActividadModificacionComando;
import co.edu.uco.sibe.aplicacion.comando.ParticipanteComando;
import co.edu.uco.sibe.aplicacion.comando.manejador.*;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_AREA_ADMIN_OR_ADMIN_CREATE_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.textoAUUID;

@RestController
@AllArgsConstructor
@RequestMapping(ACTIVIDADES)
public class ActividadComandoControlador {

    private final GuardarActividadManejador guardarActividadManejador;
    private final ModificarActividadManejador modificarActividadManejador;
    private final IniciarActividadManejador iniciarActividadManejador;
    private final FinalizarActividadManejador finalizarActividadManejador;
    private final CancelarActividadManejador cancelarActividadManejador;

    @PreAuthorize(HAS_AREA_ADMIN_OR_ADMIN_CREATE_AUTHORITY)
    @PostMapping
    public ComandoRespuesta<UUID> guardar(@RequestBody ActividadComando comando) {
        return this.guardarActividadManejador.ejecutar(comando);
    }

    @PreAuthorize(HAS_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY)
    @PutMapping(IDENTIFICADOR_PATH)
    public ComandoRespuesta<UUID> modificar(@RequestBody ActividadModificacionComando comando, @PathVariable String identificador) {
        return this.modificarActividadManejador.ejecutar(comando, textoAUUID(identificador));
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY)
    @PutMapping(ACTIVIDAD_INICIAR)
    public ComandoRespuesta<UUID> iniciar(@PathVariable String identificador) {
        return this.iniciarActividadManejador.ejecutar(textoAUUID(identificador));
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY)
    @PutMapping(ACTIVIDAD_FINALIZAR)
    public ComandoRespuesta<UUID> finalizar(@RequestBody List<ParticipanteComando> comandos, @PathVariable String identificador) {
        return this.finalizarActividadManejador.ejecutar(comandos, textoAUUID(identificador));
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY)
    @PutMapping(ACTIVIDAD_CANCELAR)
    public ComandoRespuesta<UUID> cancelar(@PathVariable String identificador) {
        return this.cancelarActividadManejador.ejecutar(textoAUUID(identificador));
    }
}