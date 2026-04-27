package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.IniciarActividadUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class IniciarActividadManejador implements ManejadorComandoRespuesta<UUID, ComandoRespuesta<UUID>> {
    private final IniciarActividadUseCase iniciarActividadUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(UUID comando) {
        return new ComandoRespuesta<>(iniciarActividadUseCase.ejecutar(comando));
    }
}