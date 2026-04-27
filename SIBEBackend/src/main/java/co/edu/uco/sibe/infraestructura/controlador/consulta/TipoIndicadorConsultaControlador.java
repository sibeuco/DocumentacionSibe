package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarTiposIndicadorManejador;
import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.TIPOS_INDICADOR;

@RestController
@AllArgsConstructor
@RequestMapping(TIPOS_INDICADOR)
public class TipoIndicadorConsultaControlador {
    private final ConsultarTiposIndicadorManejador consultarTiposIndicadorManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping
    public List<TipoIndicadorDTO> consultarTodos() {
        return consultarTiposIndicadorManejador.ejecutar();
    }
}