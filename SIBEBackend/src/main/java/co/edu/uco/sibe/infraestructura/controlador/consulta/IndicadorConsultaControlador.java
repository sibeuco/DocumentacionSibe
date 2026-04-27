package co.edu.uco.sibe.infraestructura.controlador.consulta;

import co.edu.uco.sibe.aplicacion.consulta.ConsultarIndicadoresManejador;
import co.edu.uco.sibe.aplicacion.consulta.ConsultarIndicadoresParaActividadesManejador;
import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.SeguridadConstante.HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.INDICADORES;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.INDICADORES_PARA_ACTIVIDADES;
import static co.edu.uco.sibe.dominio.transversal.constante.ApiEndpointConstante.INDICADORES_TODOS;

@RestController
@AllArgsConstructor
@RequestMapping(INDICADORES)
public class IndicadorConsultaControlador {
    private final ConsultarIndicadoresManejador consultarIndicadoresManejador;
    private final ConsultarIndicadoresParaActividadesManejador consultarIndicadoresParaActividadesManejador;

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping
    public Page<IndicadorDTO> consultarTodos(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamano){
        return consultarIndicadoresManejador.ejecutar(pagina, tamano);
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(INDICADORES_TODOS)
    public List<IndicadorDTO> consultarTodosSinPaginar(){
        return consultarIndicadoresManejador.ejecutar();
    }

    @PreAuthorize(HAS_USER_OR_AREA_ADMIN_OR_ADMIN_GET_AUTHORITY)
    @GetMapping(INDICADORES_PARA_ACTIVIDADES)
    public List<IndicadorDTO> consultarParaActividades(){
        return consultarIndicadoresParaActividadesManejador.ejecutar();
    }
}