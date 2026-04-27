package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.SubareaFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarSubareaUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarSubareaManejador implements ManejadorComandoRespuesta<String, ComandoRespuesta<UUID>> {
    private final GuardarSubareaUseCase guardarSubareaUseCase;
    private final SubareaFabrica subareaFabrica;

    @Override
    public ComandoRespuesta<UUID> ejecutar(String comando) {
        var subarea = this.subareaFabrica.construir(comando, new ArrayList<>());
        return new ComandoRespuesta<>(
                this.guardarSubareaUseCase.ejecutar(
                        this.subareaFabrica.construir(
                                comando,
                                new ArrayList<>()
                        )
                )
        );
    }
}