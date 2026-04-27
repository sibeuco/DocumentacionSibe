package co.edu.uco.sibe.aplicacion.comando.manejador;

import co.edu.uco.sibe.aplicacion.transversal.ComandoRespuesta;
import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.usecase.comando.LoginUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class LoginManejador implements ManejadorComandoRespuesta<String, ComandoRespuesta<UUID>> {
    private final LoginUseCase loginUseCase;

    @Override
    public ComandoRespuesta<UUID> ejecutar(String command) {
        return new ComandoRespuesta<>(this.loginUseCase.ejecutar(command));
    }
}