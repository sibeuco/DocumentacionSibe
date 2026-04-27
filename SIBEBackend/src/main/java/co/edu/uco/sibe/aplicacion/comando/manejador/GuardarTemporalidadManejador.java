package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.TemporalidadFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarTemporalidadUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarTemporalidadManejador implements ManejadorComandoRespuesta<String, ComandoRespuesta<UUID>> {
    private final GuardarTemporalidadUseCase guardarTemporalidadUseCase;
    private final TemporalidadFabrica temporalidadFabrica;

    @Override
    public ComandoRespuesta<UUID> ejecutar(String comando) {
        return new ComandoRespuesta<>(
                this.guardarTemporalidadUseCase.ejecutar(
                        this.temporalidadFabrica.construir(comando)
                )
        );
    }
}