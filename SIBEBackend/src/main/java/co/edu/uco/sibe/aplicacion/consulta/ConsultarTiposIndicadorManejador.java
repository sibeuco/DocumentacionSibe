package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.TipoIndicadorDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIndicadorRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarTiposIndicadorManejador implements ManejadorRespuesta<List<TipoIndicadorDTO>> {
    private final TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;

    @Override
    public List<TipoIndicadorDTO> ejecutar() {
        return this.tipoIndicadorRepositorioConsulta.consultarDTOs();
    }
}