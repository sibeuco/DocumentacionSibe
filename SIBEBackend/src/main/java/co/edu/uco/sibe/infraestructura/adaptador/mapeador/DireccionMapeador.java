package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.DireccionEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class DireccionMapeador {
    private final AreaMapeador areaMapeador;
    private final ActividadMapeador actividadMapeador;

    public DireccionEntidad construirEntidad(Direccion direccion) {
        return new DireccionEntidad(
                direccion.getIdentificador(),
                direccion.getNombre(),
                this.areaMapeador.construirEntidades(direccion.getAreas()),
                this.actividadMapeador.construirEntidades(direccion.getActividades())
        );
    }


    public Direccion construirModelo(DireccionEntidad direccion) {
        return Direccion.construir(
                direccion.getIdentificador(),
                direccion.getNombre(),
                this.areaMapeador.construirModelos(direccion.getAreas()),
                this.actividadMapeador.construirModelos(direccion.getActividades())
        );
    }

    public DireccionDTO construirDTO(DireccionEntidad direccion){
        return new DireccionDTO(direccion.getIdentificador().toString(), direccion.getNombre());
    }

    public List<DireccionDTO> construirDTOs(List<DireccionEntidad> direcciones){
        return direcciones.stream().map(this::construirDTO).toList();
    }
}