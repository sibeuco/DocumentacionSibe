package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.TemporalidadDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.TemporalidadRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarTemporalidadesManejador implements ManejadorRespuesta<List<TemporalidadDTO>> {
    private final TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;

    @Override
    public List<TemporalidadDTO> ejecutar() {
        return this.temporalidadRepositorioConsulta.consultarDTOs();
    }
}