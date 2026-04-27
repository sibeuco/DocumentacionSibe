package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.SubareaEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class SubareaDetalladaMapeador {

    private final ActividadDetalladaMapeador actividadDetalladaMapeador;

    public SubareaDetalladaDTO construirDTO(SubareaEntidad entidad) {
        return new SubareaDetalladaDTO(
                entidad.getIdentificador().toString(),
                entidad.getNombre(),
                actividadDetalladaMapeador.construirDTOs(entidad.getActividades()),
                null,
                null
        );
    }

    public List<SubareaDetalladaDTO> construirDTOs(List<SubareaEntidad> entidades) {
        return entidades.stream().map(this::construirDTO).toList();
    }
}