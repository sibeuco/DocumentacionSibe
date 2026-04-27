package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarSubareasDTOManejador implements ManejadorRespuesta<List<SubareaDTO>> {
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;

    @Override
    public List<SubareaDTO> ejecutar() {
        return this.subareaRepositorioConsulta.consultarDTOs();
    }
}