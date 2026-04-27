package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarPublicosInteresManejador;
import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.PUBLICOS_INTERES;

@RestController
@AllArgsConstructor
@RequestMapping(PUBLICOS_INTERES)
public class PublicoInteresConsultaControlador {
    private final ConsultarPublicosInteresManejador consultarPublicosInteresManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping
    public List<PublicoInteresDTO> consultarTodos(){
        return consultarPublicosInteresManejador.ejecutar();
    }
}