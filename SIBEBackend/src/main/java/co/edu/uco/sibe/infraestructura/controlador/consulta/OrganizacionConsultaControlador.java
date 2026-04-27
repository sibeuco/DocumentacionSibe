package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ContarUsuariosPorOrganizacionManejador;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY;

@RestController
@AllArgsConstructor
@RequestMapping(ORGANIZACION)
public class OrganizacionConsultaControlador {

    private final ContarUsuariosPorOrganizacionManejador contarUsuariosPorOrganizacionManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(CONTAR_USUARIOS_PATH)
    public ComandoRespuesta<Long> contarUsuarios(@PathVariable String identificador) {
        return this.contarUsuariosPorOrganizacionManejador.ejecutar(identificador);
    }
}