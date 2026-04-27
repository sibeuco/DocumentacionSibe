package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.CancelarActividadUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CancelarActividadManejador implements ManejadorComandoRespuesta<UUID, ComandoRespuesta<UUID>> {
    private final CancelarActividadUseCase cancelarActividadUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(UUID comando) {
        return new ComandoRespuesta<>(cancelarActividadUseCase.ejecutar(comando));
    }
}