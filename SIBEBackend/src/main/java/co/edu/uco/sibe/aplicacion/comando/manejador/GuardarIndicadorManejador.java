package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.IndicadorComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.IndicadorFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarIndicadorUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarIndicadorManejador implements ManejadorComandoRespuesta<IndicadorComando, ComandoRespuesta<UUID>> {
    private final IndicadorFabrica indicadorFabrica;
    private final GuardarIndicadorUseCase guardarIndicadorUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(IndicadorComando comando) {
        var indicador = this.indicadorFabrica.construir(comando);

        return new ComandoRespuesta<>(
                this.guardarIndicadorUseCase.ejecutar(
                        indicador
                )
        );
    }
}