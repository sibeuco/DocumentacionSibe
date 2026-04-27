package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.AccionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.AccionFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarAccionUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarAccionManejador implements ManejadorComandoRespuesta<AccionComando, ComandoRespuesta<UUID>> {
    private final AccionFabrica accionFabrica;
    private final GuardarAccionUseCase guardarAccionUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(AccionComando comando) {
        var accion = this.accionFabrica.construir(comando);

        return new ComandoRespuesta<>(
                this.guardarAccionUseCase.ejecutar(
                        accion
                )
        );
    }
}