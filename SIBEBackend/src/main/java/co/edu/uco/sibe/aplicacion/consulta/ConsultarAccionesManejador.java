package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.AccionDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.AccionRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class ConsultarAccionesManejador {
    private final AccionRepositorioConsulta accionRepositorioConsulta;

    @Transactional
    public Page<AccionDTO> ejecutar(int pagina, int tamano) {
        return this.accionRepositorioConsulta.consultarDTOsPaginado(pagina, tamano);
    }
}