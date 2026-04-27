package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.EjecucionActividadDetalladaDTO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RegistroAsistenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EjecucionActividadDetalladaMapeador {

    private final EstadoActividadMapeador estadoActividadMapeador;
    private final ParticipanteDetalladoMapeador participanteDetalladoMapeador;
    private final RegistroAsistenciaDAO registroAsistenciaDAO;

    public EjecucionActividadDetalladaDTO construirDTO(EjecucionActividadEntidad entidad) {
        var registros = registroAsistenciaDAO.findAllByEjecucionActividadIdentificador(entidad.getIdentificador());
        var participantes = registros.stream()
                .map(registro -> participanteDetalladoMapeador.construirDTO(registro.getParticipante()))
                .collect(Collectors.toList());

        return new EjecucionActividadDetalladaDTO(
                entidad.getIdentificador().toString(),
                entidad.getFechaProgramada().toString(),
                entidad.getFechaRealizacion() != null ? entidad.getFechaRealizacion().toString() : null,
                entidad.getHoraInicio() != null ? entidad.getHoraInicio().toString() : null,
                entidad.getHoraFin() != null ? entidad.getHoraFin().toString() : null,
                estadoActividadMapeador.construirDTO(entidad.getEstadoActividad().getEstadoActividad()),
                participantes
        );
    }

    public List<EjecucionActividadDetalladaDTO> construirDTOs(List<EjecucionActividadEntidad> entidades) {
        return entidades.stream().map(this::construirDTO).toList();
    }
}