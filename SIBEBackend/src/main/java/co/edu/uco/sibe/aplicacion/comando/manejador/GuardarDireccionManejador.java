package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.DireccionBaseComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.DireccionFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarDireccionUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarDireccionManejador implements ManejadorComandoRespuesta<DireccionBaseComando, ComandoRespuesta<UUID>> {
    private final GuardarDireccionUseCase guardarDireccionUseCase;
    private final DireccionFabrica direccionFabrica;

    @Override
    public ComandoRespuesta<UUID> ejecutar(DireccionBaseComando comando) {
        return new ComandoRespuesta<>(
                this.guardarDireccionUseCase.ejecutar(
                        this.direccionFabrica.construir(
                                comando.getNombre(),
                                comando.getAreas(),
                                new ArrayList<>()
                        )
                )
        );
    }
}