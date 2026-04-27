package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.TipoUsuarioDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarTiposUsuarioManejador implements ManejadorRespuesta<List<TipoUsuarioDTO>> {
    private final TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    @Override
    public List<TipoUsuarioDTO> ejecutar() {
        return this.tipoUsuarioRepositorioConsulta.consultarDTOs();
    }
}