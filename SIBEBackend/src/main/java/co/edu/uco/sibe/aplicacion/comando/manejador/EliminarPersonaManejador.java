package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorParametroRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.EliminarUsuarioUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class EliminarPersonaManejador implements ManejadorParametroRespuesta<UUID, ComandoRespuesta<UUID>> {
    private final EliminarUsuarioUseCase eliminarUsuarioUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(UUID parametro) {
        return new ComandoRespuesta<>(this.eliminarUsuarioUseCase.ejecutar(parametro));
    }
}