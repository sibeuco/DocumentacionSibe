package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarTiposParticipantesEnEjecucionesFinalizadasUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarTiposParticipantesEnEjecucionesFinalizadasManejador implements ManejadorRespuesta<List<String>> {
    private final ConsultarTiposParticipantesEnEjecucionesFinalizadasUseCase useCase;

    @Override
    public List<String> ejecutar() {
        return useCase.ejecutar();
    }
}