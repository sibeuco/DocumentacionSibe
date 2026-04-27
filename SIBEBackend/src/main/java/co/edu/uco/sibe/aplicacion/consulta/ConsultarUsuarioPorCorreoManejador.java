package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorParametroRespuesta;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarUsuarioPorCorreoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarUsuarioPorCorreoManejador implements ManejadorParametroRespuesta<String, UsuarioDTO> {
    private final ConsultarUsuarioPorCorreoUseCase consultarUsuarioPorCorreoUseCase;

    @Override
    public UsuarioDTO ejecutar(String parametro) {
        return this.consultarUsuarioPorCorreoUseCase.ejecutar(parametro);
    }
}