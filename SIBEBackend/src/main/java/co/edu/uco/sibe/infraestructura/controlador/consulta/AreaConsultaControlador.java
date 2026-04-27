package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarAreaDetalladaManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarAreaPorNombreDTOManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarAreasManejador;
import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
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
@RequestMapping(AREA)
public class AreaConsultaControlador {
    private final ConsultarAreasManejador consultarAreasManejador;
    private final ConsultarAreaDetalladaManejador consultarAreaDetalladaManejador;
    private final ConsultarAreaPorNombreDTOManejador consultarAreaPorNombreDTOManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(RUTA_DETALLE)
    public AreaDetalladaDTO consultarDetalle(@PathVariable String identificador) {
        return this.consultarAreaDetalladaManejador.ejecutar(identificador);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping
    public List<AreaDTO> consultarTodos(){
        return consultarAreasManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(RUTA_NOMBRE)
    public AreaDTO consultarPorNombre(@PathVariable String nombre) {
        return this.consultarAreaPorNombreDTOManejador.ejecutar(nombre);
    }
}