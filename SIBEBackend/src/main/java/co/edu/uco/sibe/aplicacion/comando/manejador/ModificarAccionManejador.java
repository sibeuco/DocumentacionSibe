package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.AccionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.AccionFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoParametroRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarAccionUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ModificarAccionManejador implements ManejadorComandoParametroRespuesta<AccionComando, UUID, ComandoRespuesta<UUID>> {
    private final AccionFabrica accionFabrica;
    private final ModificarAccionUseCase modificarAccionUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(AccionComando comando, UUID parametro) {
        var accion = this.accionFabrica.construirActualizar(comando, parametro);

        return new ComandoRespuesta<>(
                this.modificarAccionUseCase.ejecutar(
                        accion,
                        parametro
                )
        );
    }
}