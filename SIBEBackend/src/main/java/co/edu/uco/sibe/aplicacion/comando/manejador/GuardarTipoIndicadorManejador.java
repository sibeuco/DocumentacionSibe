package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.TipoIndicadorComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.TipoIndicadorFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarTipoIndicadorUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarTipoIndicadorManejador  implements ManejadorComandoRespuesta<TipoIndicadorComando, ComandoRespuesta<UUID>> {
    private final GuardarTipoIndicadorUseCase guardarTipoIndicadorUseCase;
    private final TipoIndicadorFabrica tipoIndicadorFabrica;

    @Override
    public ComandoRespuesta<UUID> ejecutar(TipoIndicadorComando comando) {
        return new ComandoRespuesta<>(
                this.guardarTipoIndicadorUseCase.ejecutar(
                        this.tipoIndicadorFabrica.construir(comando)
                )
        );
    }
}