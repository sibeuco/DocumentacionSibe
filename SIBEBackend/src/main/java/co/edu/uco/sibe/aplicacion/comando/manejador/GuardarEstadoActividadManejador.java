package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.EstadoActividadFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarEstadoActividadUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarEstadoActividadManejador implements ManejadorComandoRespuesta<String, ComandoRespuesta<UUID>> {
    private final GuardarEstadoActividadUseCase guardarEstadoActividadUseCase;
    private final EstadoActividadFabrica estadoActividadFabrica;

    @Override
    public ComandoRespuesta<UUID> ejecutar(String comando) {
        return new ComandoRespuesta<>(
                this.guardarEstadoActividadUseCase.ejecutar(
                        this.estadoActividadFabrica.construir(comando)
                )
        );
    }
}