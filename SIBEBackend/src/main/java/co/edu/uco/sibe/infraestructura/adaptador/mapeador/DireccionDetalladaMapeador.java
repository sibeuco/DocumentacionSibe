package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.DireccionEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class DireccionDetalladaMapeador {

    private final AreaDetalladaMapeador areaDetalladaMapeador;
    private final ActividadDetalladaMapeador actividadDetalladaMapeador;

    public DireccionDetalladaDTO construirDTO(DireccionEntidad entidad) {
        return new DireccionDetalladaDTO(
                entidad.getIdentificador(),
                entidad.getNombre(),
                areaDetalladaMapeador.construirDTOs(entidad.getAreas()),
                actividadDetalladaMapeador.construirDTOs(entidad.getActividades())
        );
    }

    public List<DireccionDetalladaDTO> construirDTOs(List<DireccionEntidad> entidades) {
        return entidades.stream().map(this::construirDTO).toList();
    }
}