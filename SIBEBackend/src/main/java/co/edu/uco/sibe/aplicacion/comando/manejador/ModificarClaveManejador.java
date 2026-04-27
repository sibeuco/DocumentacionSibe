package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ClaveModificacionComando;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarClaveUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.textoAUUID;

@Component
@AllArgsConstructor
public class ModificarClaveManejador implements ManejadorComandoRespuesta<ClaveModificacionComando, ComandoRespuesta<UUID>> {
    private final ModificarClaveUseCase modificarClaveUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(ClaveModificacionComando comando) {
        return new ComandoRespuesta<>(
                this.modificarClaveUseCase.ejecutar(
                        comando.getClaveAntigua(),
                        comando.getClaveNueva(),
                        textoAUUID(comando.getIdentificador())
                )
        );
    }
}