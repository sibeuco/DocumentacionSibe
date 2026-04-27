package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.SolicitarCodigoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class SolicitarCodigoManejador implements ManejadorComandoRespuesta<String, ComandoRespuesta<UUID>> {
    private final SolicitarCodigoUseCase solicitarCodigoUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(String comando) {
        return new ComandoRespuesta<>(
                this.solicitarCodigoUseCase.ejecutar(comando)
        );
    }
}