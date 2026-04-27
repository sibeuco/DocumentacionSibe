package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarSubareaDetalladaManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarSubareaPorNombreDTOManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarSubareasDTOManejador;
import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
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
@RequestMapping(SUBAREA)
public class SubareaConsultaControlador {
    private final ConsultarSubareasDTOManejador consultarSubareasDTOManejador;
    private final ConsultarSubareaDetalladaManejador consultarSubareaDetalladaManejador;
    private final ConsultarSubareaPorNombreDTOManejador consultarSubareaPorNombreDTOManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(RUTA_DETALLE)
    public SubareaDetalladaDTO consultarDetalle(@PathVariable String identificador) {
        return this.consultarSubareaDetalladaManejador.ejecutar(identificador);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping
    public List<SubareaDTO> consultarTodos(){
        return consultarSubareasDTOManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(RUTA_NOMBRE)
    public SubareaDTO consultarPorNombre(@PathVariable String nombre) {
        return this.consultarSubareaPorNombreDTOManejador.ejecutar(nombre);
    }
}