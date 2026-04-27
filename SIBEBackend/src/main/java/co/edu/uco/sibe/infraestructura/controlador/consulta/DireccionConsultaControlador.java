package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarDireccionDetalladaManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarDireccionPorNombreDTOManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarDireccionesManejador;
import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY;

@RestController
@AllArgsConstructor
@RequestMapping(DIRECCION)
public class DireccionConsultaControlador {
    private final ConsultarDireccionesManejador consultarDireccionesManejador;
    private final ConsultarDireccionDetalladaManejador consultarDireccionDetalladaManejador;
    private final ConsultarDireccionPorNombreDTOManejador consultarDireccionPorNombreDTOManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(RUTA_DETALLE)
    public DireccionDetalladaDTO consultarDetalle(@PathVariable String identificador) {
        return this.consultarDireccionDetalladaManejador.ejecutar(identificador);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping
    public List<DireccionDTO> consultarTodos(){
        return consultarDireccionesManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(RUTA_NOMBRE)
    public DireccionDTO consultarPorNombre(@PathVariable String nombre) {
        return this.consultarDireccionPorNombreDTOManejador.ejecutar(nombre);
    }
}