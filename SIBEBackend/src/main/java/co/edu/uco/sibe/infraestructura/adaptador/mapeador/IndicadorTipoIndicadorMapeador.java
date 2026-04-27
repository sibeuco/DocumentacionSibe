package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorTipoIndicadorDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorTipoIndicadorEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class IndicadorTipoIndicadorMapeador {
    private final TipoIndicadorMapeador tipoIndicadorMapeador;
    private final IndicadorTipoIndicadorDAO indicadorTipoIndicadorDAO;

    public IndicadorTipoIndicadorEntidad construirEntidad(TipoIndicador tipoIndicador) {
        return new IndicadorTipoIndicadorEntidad(
                generar(uuid -> !esNulo(indicadorTipoIndicadorDAO.findById(uuid).orElse(null))),
                this.tipoIndicadorMapeador.construirEntidad(tipoIndicador)
        );
    }

    public TipoIndicador construirModelo(IndicadorTipoIndicadorEntidad tipoIndicador) {
        return tipoIndicadorMapeador.construirModelo(tipoIndicador.getTipoIndicador());
    }

    public void actualizarEntidad(IndicadorTipoIndicadorEntidad entidad, TipoIndicador tipoIndicador) {
        entidad.setTipoIndicador(this.tipoIndicadorMapeador.construirEntidad(tipoIndicador));
    }

    public TipoIndicadorDTO construirDTO(IndicadorTipoIndicadorEntidad tipoIndicador) {
        return tipoIndicadorMapeador.construirDTO(tipoIndicador.getTipoIndicador());
    }
}