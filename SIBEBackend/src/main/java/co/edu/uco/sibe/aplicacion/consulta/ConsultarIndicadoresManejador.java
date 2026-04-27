package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.IndicadorRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarIndicadoresManejador {
    private final IndicadorRepositorioConsulta indicadorRepositorioConsulta;

    @Transactional
    public Page<IndicadorDTO> ejecutar(int pagina, int tamano) {
        return this.indicadorRepositorioConsulta.consultarDTOsPaginado(pagina, tamano);
    }

    @Transactional
    public List<IndicadorDTO> ejecutar() {
        return this.indicadorRepositorioConsulta.consultarDTOs();
    }
}