package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.SubareaEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class SubareaMapeador {
    private final ActividadMapeador actividadMapeador;

    public SubareaEntidad construirEntidad(Subarea subarea) {
        return new SubareaEntidad(
                subarea.getIdentificador(),
                subarea.getNombre(),
                this.actividadMapeador.construirEntidades(subarea.getActividades())
        );
    }

    public List<SubareaEntidad> construirEntidades(List<Subarea> subareas) {
        return subareas.stream().map(this::construirEntidad).toList();
    }

    public Subarea construirModelo(SubareaEntidad subarea) {
        return Subarea.construir(
                subarea.getIdentificador(),
                subarea.getNombre(),
                this.actividadMapeador.construirModelos(subarea.getActividades())
        );
    }

    public List<Subarea> construirModelos(List<SubareaEntidad> subareas) {
        return subareas.stream().map(this::construirModelo).toList();
    }

    public SubareaDTO construirDTO(SubareaEntidad subarea){
        return new SubareaDTO(subarea.getIdentificador().toString(), subarea.getNombre());
    }

    public List<SubareaDTO> construirDTOs(List<SubareaEntidad> subareas){
        return subareas.stream().map(this::construirDTO).toList();
    }
}