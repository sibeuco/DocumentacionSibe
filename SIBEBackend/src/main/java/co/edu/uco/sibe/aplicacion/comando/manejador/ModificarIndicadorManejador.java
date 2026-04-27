package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.IndicadorComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.IndicadorFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoParametroRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.ModificarIndicadorUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ModificarIndicadorManejador  implements ManejadorComandoParametroRespuesta<IndicadorComando, UUID, ComandoRespuesta<UUID>> {
    private final IndicadorFabrica indicadorFabrica;
    private final ModificarIndicadorUseCase modificarIndicadorUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(IndicadorComando comando, UUID parametro) {
        var indicador = this.indicadorFabrica.construirActualizar(comando, parametro);

        return new ComandoRespuesta<>(
                this.modificarIndicadorUseCase.ejecutar(
                        indicador,
                        parametro
                )
        );
    }
}