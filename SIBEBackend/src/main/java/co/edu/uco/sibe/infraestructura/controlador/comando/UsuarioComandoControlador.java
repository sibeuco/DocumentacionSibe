package co.edu.uco.sibe.infraestructura.controlador.comando;

import co.edu.uco.sibe.aplicacion.comando.*;
import co.edu.uco.sibe.aplicacion.comando.manejador.*;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.*;

@RestController
@AllArgsConstructor
@RequestMapping(USER_API)
public class UsuarioComandoControlador {
    private final GuardarUsuarioManejador guardarUsuarioManejador;
    private final EliminarPersonaManejador eliminarPersonaManejador;
    private final ModificarUsuarioManejador modificarUsuarioManejador;
    private final SolicitarCodigoManejador solicitarCodigoManejador;
    private final ValidarCodigoRecuperacionClaveManejador validarCodigoRecuperacionClaveManejador;
    private final RecuperarClaveManejador recuperarClaveManejador;
    private final ModificarClaveManejador modificarClaveManejador;

    @PreAuthorize(HAS_ADMIN_CREATE_AUTHORITY)
    @PostMapping
    public ComandoRespuesta<UUID> guardar(@RequestBody UsuarioComando usuario){
        return this.guardarUsuarioManejador.ejecutar(usuario);
    }

    @PreAuthorize(HAS_USER_OR_ADMIN_UPDATE_AUTHORITY)
    @PutMapping(USUARIO_IDENTIFICADOR)
    public ComandoRespuesta<UUID> modificar(@RequestBody UsuarioModificacionComando usuario, @PathVariable(IDENTIFICADOR) UUID identificador){
        return modificarUsuarioManejador.ejecutar(usuario, identificador);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_UPDATE_AUTHORITY)
    @PutMapping(MODIFICAR_CLAVE_PATH)
    public ComandoRespuesta<UUID> modificarClave(@RequestBody ClaveModificacionComando claveModificacionComando) {
        return this.modificarClaveManejador.ejecutar(claveModificacionComando);
    }

    @PreAuthorize(HAS_ADMIN_DELETE_AUTHORITY)
    @DeleteMapping(USUARIO_IDENTIFICADOR)
    public ComandoRespuesta<UUID> eliminar(@PathVariable UUID identificador){
        return this.eliminarPersonaManejador.ejecutar(identificador);
    }

    @PostMapping(SOLICITAR_CODIGO_PATH)
    public ComandoRespuesta<UUID> solicitarCodigo(@PathVariable String correo) {
        return this.solicitarCodigoManejador.ejecutar(correo);
    }

    @PostMapping(VALIDAR_CODIGO_PATH)
    public ComandoRespuesta<Boolean> validarCodigo(@RequestBody ValidarCodigoRecuperacionClaveComando comando) {
        return this.validarCodigoRecuperacionClaveManejador.ejecutar(comando);
    }

    @PutMapping(RECUPERAR_CLAVE_PATH)
    public ComandoRespuesta<UUID> recuperarClave(@RequestBody RecuperarClaveComando comando) {
        return this.recuperarClaveManejador.ejecutar(comando);
    }
}