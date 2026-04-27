package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.TipoIdentificacionDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarTiposIdentificacionManejador implements ManejadorRespuesta<List<TipoIdentificacionDTO>> {
    private final TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    @Override
    public List<TipoIdentificacionDTO> ejecutar() {
        return this.tipoIdentificacionRepositorioConsulta.consultarDTOs();
    }
}