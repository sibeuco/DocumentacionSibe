package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.EstadisticaMesDTO;
import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarEstadisticasParticipantesPorMesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarEstadisticasParticipantesPorMesManejador implements ManejadorComandoRespuesta<FiltroEstadisticaDTO, List<EstadisticaMesDTO>> {
    private final ConsultarEstadisticasParticipantesPorMesUseCase useCase;

    @Override
    public List<EstadisticaMesDTO> ejecutar(FiltroEstadisticaDTO filtro) {
        return useCase.ejecutar(filtro);
    }
}