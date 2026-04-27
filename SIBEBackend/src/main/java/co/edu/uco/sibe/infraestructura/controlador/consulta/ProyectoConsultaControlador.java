package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarProyectosManejador;
import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.*;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY;

@RestController
@AllArgsConstructor
@RequestMapping(PROYECTOS)
public class ProyectoConsultaControlador {
    private final ConsultarProyectosManejador consultarProyectosManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping
    public Page<ProyectoDTO> consultarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano){
        return consultarProyectosManejador.ejecutar(pagina, tamano);
    }
}