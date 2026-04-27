package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorTemporalidadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorTemporalidadEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class IndicadorTemporalidadMapeador {
    private final TemporalidadMapeador temporalidadMapeador;
    private final IndicadorTemporalidadDAO indicadorTemporalidadDAO;

    public IndicadorTemporalidadEntidad construirEntidad(Temporalidad temporalidad) {
        return new IndicadorTemporalidadEntidad(
                generar(uuid -> !esNulo(indicadorTemporalidadDAO.findById(uuid).orElse(null))),
                this.temporalidadMapeador.construirEntidad(temporalidad)
        );
    }

    public Temporalidad construirModelo(IndicadorTemporalidadEntidad temporalidad) {
        return this.temporalidadMapeador.construirModelo(temporalidad.getTemporalidad());
    }

    public void actualizarEntidad(IndicadorTemporalidadEntidad entidad, Temporalidad temporalidad) {
        entidad.setTemporalidad(this.temporalidadMapeador.construirEntidad(temporalidad));
    }

    public TemporalidadDTO construirDTO(IndicadorTemporalidadEntidad temporalidad) {
        return this.temporalidadMapeador.construirDTO(temporalidad.getTemporalidad());
    }
}