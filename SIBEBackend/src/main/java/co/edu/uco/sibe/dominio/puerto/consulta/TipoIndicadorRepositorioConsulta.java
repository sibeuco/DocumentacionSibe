package co.edu.uco.sibe.dominio.puerto.consulta;

import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import java.util.List;
import java.util.UUID;

public interface TipoIndicadorRepositorioConsulta {
    List<TipoIndicadorDTO> consultarDTOs();

    TipoIndicador consultarPorIdentificador(UUID identificador);

    TipoIndicador consultarPorNaturaleza(String naturaleza);

    TipoIndicador consultarPorTipologia(String tipologia);

    Boolean hayDatos();
}