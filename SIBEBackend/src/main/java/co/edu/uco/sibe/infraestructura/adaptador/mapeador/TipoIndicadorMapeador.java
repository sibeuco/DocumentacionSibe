package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIndicadorEntidad;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TipoIndicadorMapeador {
    public TipoIndicadorEntidad construirEntidad(TipoIndicador tipoIndicador){
        return new TipoIndicadorEntidad(tipoIndicador.getIdentificador(), tipoIndicador.getNaturaleza(), tipoIndicador.getTipologia());
    }

    public TipoIndicador construirModelo(TipoIndicadorEntidad tipoIndicador){
        return TipoIndicador.construir(tipoIndicador.getIdentificador(), tipoIndicador.getNaturaleza(), tipoIndicador.getTipologia());
    }

    public TipoIndicadorDTO construirDTO(TipoIndicadorEntidad tipoIndicador) {
        return new TipoIndicadorDTO(tipoIndicador.getIdentificador().toString(), tipoIndicador.getNaturaleza(), tipoIndicador.getTipologia());
    }

    public List<TipoIndicadorDTO> construirDTOs(List<TipoIndicadorEntidad> entidades) {
        return entidades.stream().map(this::construirDTO).toList();
    }
}