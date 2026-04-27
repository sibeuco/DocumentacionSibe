package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ContarEjecucionesTotalesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContarEjecucionesTotalesManejador implements ManejadorComandoRespuesta<FiltroEstadisticaDTO, Long> {
    private final ContarEjecucionesTotalesUseCase useCase;

    @Override
    public Long ejecutar(FiltroEstadisticaDTO filtro) {
        return useCase.ejecutar(filtro);
    }
}