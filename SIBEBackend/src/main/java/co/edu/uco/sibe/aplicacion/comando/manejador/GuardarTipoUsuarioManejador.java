package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.TipoUsuarioComando;
import co.edu.uco.sibe.aplicacion.comando.fabrica.TipoUsuarioFabrica;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.GuardarTipoUsuarioUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class GuardarTipoUsuarioManejador implements ManejadorComandoRespuesta<TipoUsuarioComando, ComandoRespuesta<UUID>> {
    private final GuardarTipoUsuarioUseCase guardarTipoUsuarioUseCase;
    private final TipoUsuarioFabrica tipoUsuarioFabrica;

    @Override
    public ComandoRespuesta<UUID> ejecutar(TipoUsuarioComando comando) {
        return new ComandoRespuesta<>(
                this.guardarTipoUsuarioUseCase.ejecutar(
                        this.tipoUsuarioFabrica.construir(comando)
                )
        );
    }
}