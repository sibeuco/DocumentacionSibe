package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarActividadesPorSubareaUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarActividadesPorSubareaManejador implements ManejadorComandoRespuesta<String, List<ActividadDTO>> {
    private final ConsultarActividadesPorSubareaUseCase consultarActividadesPorSubareaUseCase;

    @Override
    public List<ActividadDTO> ejecutar(String comando) {
        return consultarActividadesPorSubareaUseCase.ejecutar(comando);
    }

    @Transactional
    public Page<ActividadDTO> ejecutar(String identificador, int pagina, int tamano) {
        return consultarActividadesPorSubareaUseCase.ejecutar(identificador, pagina, tamano);
    }
}