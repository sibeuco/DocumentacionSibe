package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorParametroRespuesta;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarUsuarioPorIdentificadorUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ConsultarUsuarioPorIdentificadorManejador implements ManejadorParametroRespuesta<UUID, UsuarioDTO> {
    private final ConsultarUsuarioPorIdentificadorUseCase consultarUsuarioPorIdentificadorUseCase;

    @Override
    public UsuarioDTO ejecutar(UUID parametro) {
        return this.consultarUsuarioPorIdentificadorUseCase.ejecutar(parametro);
    }
}