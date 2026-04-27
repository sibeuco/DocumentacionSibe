package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.EjecucionActividadDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarEjecucionesPorActividadUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarEjecucionesPorActividadManejador implements ManejadorComandoRespuesta<String, List<EjecucionActividadDTO>> {
    private final ConsultarEjecucionesPorActividadUseCase consultarEjecucionesPorActividadUseCase;

    @Override
    public List<EjecucionActividadDTO> ejecutar(String comando) {
        return consultarEjecucionesPorActividadUseCase.ejecutar(comando);
    }

    @Transactional
    public Page<EjecucionActividadDTO> ejecutar(String identificador, int pagina, int tamano) {
        return consultarEjecucionesPorActividadUseCase.ejecutar(identificador, pagina, tamano);
    }
}