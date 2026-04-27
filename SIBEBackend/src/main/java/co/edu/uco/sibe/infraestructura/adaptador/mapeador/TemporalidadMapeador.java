package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TemporalidadEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class TemporalidadMapeador {
    public TemporalidadEntidad construirEntidad(Temporalidad temporalidad){
        return new TemporalidadEntidad(temporalidad.getIdentificador(), temporalidad.getNombre());
    }

    public Temporalidad construirModelo(TemporalidadEntidad temporalidad){
        return Temporalidad.construir(temporalidad.getIdentificador(), temporalidad.getNombre());
    }

    public TemporalidadDTO construirDTO(TemporalidadEntidad temporalidad) {
        return new TemporalidadDTO(temporalidad.getIdentificador().toString(), temporalidad.getNombre());
    }

    public List<TemporalidadDTO> construirDTOs(List<TemporalidadEntidad> entidades) {
        return entidades.stream().map(this::construirDTO).toList();
    }
}