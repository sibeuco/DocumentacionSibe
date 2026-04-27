package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.comando.ValidarCodigoRecuperacionClaveComando;
import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.ValidarCodigoRecuperacionClaveUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ValidarCodigoRecuperacionClaveManejador implements ManejadorComandoRespuesta<ValidarCodigoRecuperacionClaveComando, ComandoRespuesta<Boolean>> {
    private final ValidarCodigoRecuperacionClaveUseCase validarCodigoRecuperacionClaveUseCase;

    @Override
    public ComandoRespuesta<Boolean> ejecutar(ValidarCodigoRecuperacionClaveComando comando) {
        return new ComandoRespuesta<>(
                this.validarCodigoRecuperacionClaveUseCase.ejecutar(
                        comando.getCorreo(),
                        comando.getCodigo()
                )
        );
    }
}