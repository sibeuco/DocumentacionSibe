package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.ProyectoRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class ConsultarProyectosManejador {
    private final ProyectoRepositorioConsulta proyectoRepositorioConsulta;

    @Transactional
    public Page<ProyectoDTO> ejecutar(int pagina, int tamano) {
        return this.proyectoRepositorioConsulta.consultarDTOsPaginado(pagina, tamano);
    }
}