package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.RecuperarClaveComando;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.RecuperarClaveUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class RecuperarClaveManejador implements ManejadorComandoRespuesta<RecuperarClaveComando, ComandoRespuesta<UUID>> {
    private final RecuperarClaveUseCase recuperarClaveUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(RecuperarClaveComando comando) {
        return new ComandoRespuesta<>(
                this.recuperarClaveUseCase.ejecutar(
                        comando.getCorreo(),
                        comando.getClave()
                )
        );
    }
}