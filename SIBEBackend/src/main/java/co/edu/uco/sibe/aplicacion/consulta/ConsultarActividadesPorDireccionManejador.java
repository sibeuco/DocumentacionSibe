package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarActividadesPorDireccionUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarActividadesPorDireccionManejador implements ManejadorComandoRespuesta<String, List<ActividadDTO>> {
    private final ConsultarActividadesPorDireccionUseCase consultarActividadesPorDireccionUseCase;

    @Override
    public List<ActividadDTO> ejecutar(String comando) {
        return consultarActividadesPorDireccionUseCase.ejecutar(comando);
    }

    @Transactional
    public Page<ActividadDTO> ejecutar(String identificador, int pagina, int tamano) {
        return consultarActividadesPorDireccionUseCase.ejecutar(identificador, pagina, tamano);
    }
}