package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AreaEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class AreaDetalladaMapeador {

    private final SubareaDetalladaMapeador subareaDetalladaMapeador;
    private final ActividadDetalladaMapeador actividadDetalladaMapeador;

    public AreaDetalladaDTO construirDTO(AreaEntidad entidad) {
        return new AreaDetalladaDTO(
                entidad.getIdentificador(),
                entidad.getNombre(),
                subareaDetalladaMapeador.construirDTOs(entidad.getSubareas()),
                actividadDetalladaMapeador.construirDTOs(entidad.getActividades()),
                null
        );
    }

    public List<AreaDetalladaDTO> construirDTOs(List<AreaEntidad> entidades) {
        return entidades.stream().map(this::construirDTO).toList();
    }
}