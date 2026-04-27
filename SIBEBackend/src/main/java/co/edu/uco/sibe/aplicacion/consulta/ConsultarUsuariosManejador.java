package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarUsuariosUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarUsuariosManejador implements ManejadorRespuesta<List<UsuarioDTO>> {
    private final ConsultarUsuariosUseCase consultarUsuariosUseCase;

    @Override
    public List<UsuarioDTO> ejecutar() {
        return this.consultarUsuariosUseCase.ejecutar();
    }

    @Transactional
    public Page<UsuarioDTO> ejecutar(String tipoUsuario, int pagina, int tamano, boolean excluir) {
        return this.consultarUsuariosUseCase.ejecutar(tipoUsuario, pagina, tamano, excluir);
    }
}