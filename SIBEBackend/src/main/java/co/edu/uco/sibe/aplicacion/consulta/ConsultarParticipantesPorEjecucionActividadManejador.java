package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.ParticipanteDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarParticipantesPorEjecucionActividadUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ConsultarParticipantesPorEjecucionActividadManejador implements ManejadorComandoRespuesta<UUID, List<ParticipanteDTO>> {
    private final ConsultarParticipantesPorEjecucionActividadUseCase consultarParticipantesPorEjecucionActividadUseCase;

    @Override
    public List<ParticipanteDTO> ejecutar(UUID comando) {
        return consultarParticipantesPorEjecucionActividadUseCase.ejecutar(comando);
    }

    @Transactional
    public Page<ParticipanteDTO> ejecutar(UUID identificador, int pagina, int tamano) {
        return consultarParticipantesPorEjecucionActividadUseCase.ejecutar(identificador, pagina, tamano);
    }
}
