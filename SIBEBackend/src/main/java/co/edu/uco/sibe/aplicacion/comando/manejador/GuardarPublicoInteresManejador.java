package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.fabrica.PublicoInteresFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarPublicoInteresUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarPublicoInteresManejador implements ManejadorComandoRespuesta<String, ComandoRespuesta<UUID>> {
    private final GuardarPublicoInteresUseCase guardarPublicoInteresUseCase;
    private final PublicoInteresFabrica publicoInteresFabrica;

    @Override
    public ComandoRespuesta<UUID> ejecutar(String comando) {
        return new ComandoRespuesta<>(
                this.guardarPublicoInteresUseCase.ejecutar(
                        this.publicoInteresFabrica.construir(comando)
                )
        );
    }
}