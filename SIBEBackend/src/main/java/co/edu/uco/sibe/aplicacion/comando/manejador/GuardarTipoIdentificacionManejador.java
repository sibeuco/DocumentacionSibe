package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.TipoIdentificacionComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.TipoIdentificacionFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarTipoIdentificacionUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarTipoIdentificacionManejador implements ManejadorComandoRespuesta<TipoIdentificacionComando, ComandoRespuesta<UUID>> {
    private final GuardarTipoIdentificacionUseCase guardarTipoIdentificacionUseCase;
    private final TipoIdentificacionFabrica tipoIdentificacionFabrica;

    @Override
    public ComandoRespuesta<UUID> ejecutar(TipoIdentificacionComando comando) {
        return new ComandoRespuesta<>(
                this.guardarTipoIdentificacionUseCase.ejecutar(
                        this.tipoIdentificacionFabrica.construir(comando)
                )
        );
    }
}