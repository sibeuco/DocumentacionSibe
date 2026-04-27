package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarUsuarioPorCorreoManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarUsuarioPorIdentificadorManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarUsuariosManejador;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY;

@RestController
@AllArgsConstructor
@RequestMapping(USER_API)
public class UsuarioConsultarControlador {
    private final ConsultarUsuarioPorCorreoManejador consultarUsuarioPorCorreoManejador;
    private final ConsultarUsuarioPorIdentificadorManejador consultarUsuarioPorIdentificadorManejador;
    private final ConsultarUsuariosManejador consultarUsuariosManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping()
    public List<UsuarioDTO> consultarTodos(){
        return consultarUsuariosManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping("/paginado")
    public Page<UsuarioDTO> consultarTodosPaginado(
            @RequestParam String tipoUsuario,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano,
            @RequestParam(defaultValue = "false") boolean excluir){
        return consultarUsuariosManejador.ejecutar(tipoUsuario, pagina, tamano, excluir);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(USUARIO_ID_IDENTIFICADOR)
    public UsuarioDTO consultarPorIdentificador(@PathVariable UUID identificador){
        return consultarUsuarioPorIdentificadorManejador.ejecutar(identificador);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(USUARIO_CORREO_CORREO)
    public UsuarioDTO consultarPorCorreo(@PathVariable String correo){
        return consultarUsuarioPorCorreoManejador.ejecutar(correo);
    }
}