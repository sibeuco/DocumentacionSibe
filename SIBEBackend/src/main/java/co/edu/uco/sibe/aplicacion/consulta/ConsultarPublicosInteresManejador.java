package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.PublicoInteresRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarPublicosInteresManejador  implements ManejadorRespuesta<List<PublicoInteresDTO>> {
    private final PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;

    @Override
    public List<PublicoInteresDTO> ejecutar() {
        return this.publicoInteresRepositorioConsulta.consultarDTOs();
    }
}