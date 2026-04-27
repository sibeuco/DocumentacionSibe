package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.AreaBaseComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.AreaFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarAreaUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarAreaManejador implements ManejadorComandoRespuesta<AreaBaseComando, ComandoRespuesta<UUID>> {
    private final GuardarAreaUseCase guardarAreaUseCase;
    private final AreaFabrica areaFabrica;

    @Override
    public ComandoRespuesta<UUID> ejecutar(AreaBaseComando comando) {
        return new ComandoRespuesta<>(
                this.guardarAreaUseCase.ejecutar(
                        this.areaFabrica.construir(
                                comando.getNombre(),
                                comando.getSubareas(),
                                new ArrayList<>()
                        )
                )
        );
    }
}