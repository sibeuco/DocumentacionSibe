package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.EstadisticaDTO;
import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarEstadisticasParticipantesPorEstructuraUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarEstadisticasParticipantesPorEstructuraManejador implements ManejadorComandoRespuesta<FiltroEstadisticaDTO, List<EstadisticaDTO>> {
    private final ConsultarEstadisticasParticipantesPorEstructuraUseCase useCase;

    @Override
    public List<EstadisticaDTO> ejecutar(FiltroEstadisticaDTO filtro) {
        return useCase.ejecutar(filtro);
    }
}