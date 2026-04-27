package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.AreaEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class AreaMapeador {
    private final SubareaMapeador subareaMapeador;
    private final ActividadMapeador actividadMapeador;

    public AreaEntidad construirEntidad(Area area) {
        return new AreaEntidad(
                area.getIdentificador(),
                area.getNombre(),
                this.subareaMapeador.construirEntidades(area.getSubareas()),
                this.actividadMapeador.construirEntidades(area.getActividades())
        );
    }

    public List<AreaEntidad> construirEntidades(List<Area> areas) {
        return areas.stream().map(this::construirEntidad).toList();
    }

    public Area construirModelo(AreaEntidad area) {
        return Area.construir(
                area.getIdentificador(),
                area.getNombre(),
                this.subareaMapeador.construirModelos(area.getSubareas()),
                this.actividadMapeador.construirModelos(area.getActividades())
        );
    }

    public List<Area> construirModelos(List<AreaEntidad> areas) {
        return areas.stream().map(this::construirModelo).toList();
    }

    public AreaDTO construirDTO(AreaEntidad area){
        return new AreaDTO(area.getIdentificador().toString(), area.getNombre());
    }

    public List<AreaDTO> construirDTOs(List<AreaEntidad> areas){
        return areas.stream().map(this::construirDTO).toList();
    }
}