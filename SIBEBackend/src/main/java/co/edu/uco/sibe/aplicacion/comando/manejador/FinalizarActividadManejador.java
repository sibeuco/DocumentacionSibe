package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ParticipanteComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.ParticipanteFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoParametroRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.FinalizarActividadUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class FinalizarActividadManejador implements ManejadorComandoParametroRespuesta<List<ParticipanteComando>, UUID, ComandoRespuesta<UUID>> {
    private final ParticipanteFabrica participanteFabrica;
    private final FinalizarActividadUseCase finalizarActividadUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(List<ParticipanteComando> comando, UUID parametro) {
        var participantes = participanteFabrica.construirParticipantes(comando, parametro);

        return new ComandoRespuesta<>(
                finalizarActividadUseCase.ejecutar(
                        parametro,
                        participantes
                )
        );
    }
}