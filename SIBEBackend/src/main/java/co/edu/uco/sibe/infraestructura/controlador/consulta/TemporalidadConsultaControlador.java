package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarTemporalidadesManejador;
import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.TEMPORALIDADES;

@RestController
@AllArgsConstructor
@RequestMapping(TEMPORALIDADES)
public class TemporalidadConsultaControlador {
    private final ConsultarTemporalidadesManejador consultarTemporalidadesManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping
    public List<TemporalidadDTO> consultarTodos() {
        return consultarTemporalidadesManejador.ejecutar();
    }
}