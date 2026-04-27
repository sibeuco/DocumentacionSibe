package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ContarParticipantesTotalesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContarParticipantesTotalesManejador implements ManejadorComandoRespuesta<FiltroEstadisticaDTO, Long> {
    private final ContarParticipantesTotalesUseCase useCase;

    @Override
    public Long ejecutar(FiltroEstadisticaDTO filtro) {
        return useCase.ejecutar(filtro);
    }
}