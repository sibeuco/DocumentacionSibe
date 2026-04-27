package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarMiembroPorIdCarnetManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarMiembroPorIdentificacionManejador;
import co.edu.uco.sibe.dominio.dto.MiembroDTO;
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
@RequestMapping(MIEMBROS)
public class MiembroConsultaControlador {
    private final ConsultarMiembroPorIdentificacionManejador consultarMiembroPorIdentificacionManejador;
    private final ConsultarMiembroPorIdCarnetManejador consultarMiembroPorIdCarnetManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(MIEMBRO_IDENTIFICACION)
    public MiembroDTO consultarPorIdentificacion(@PathVariable String identificacion) {
        return this.consultarMiembroPorIdentificacionManejador.ejecutar(identificacion);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(MIEMBRO_CARNET)
    public MiembroDTO consultarPorIdCarnet(@PathVariable String carnet) {
        return this.consultarMiembroPorIdCarnetManejador.ejecutar(carnet);
    }
}