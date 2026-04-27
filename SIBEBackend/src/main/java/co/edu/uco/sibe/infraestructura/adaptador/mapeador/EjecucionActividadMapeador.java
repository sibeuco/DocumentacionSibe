package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.EjecucionActividadDTO;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEntidad;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EjecucionActividadMapeador {
    private final EjecucionActividadEstadoActividadMapeador ejecucionActividadEstadoActividadMapeador;
    private final ActividadMapeador actividadMapeador;
    private final EstadoActividadMapeador estadoActividadMapeador;
    private final EjecucionActividadDAO ejecucionActividadDAO;

    public EjecucionActividadEntidad construirEntidad(EjecucionActividad dominio) {
        return new EjecucionActividadEntidad(
                dominio.getIdentificador(),
                dominio.getFechaProgramada(),
                dominio.getFechaRealizacion(),
                dominio.getHoraInicio(),
                dominio.getHoraFin(),
                ejecucionActividadEstadoActividadMapeador.construirEntidad(dominio.getEstado()),
                actividadMapeador.construirEntidad(dominio.getActividad())
        );
    }

    public EjecucionActividad construirModelo(EjecucionActividadEntidad entidad) {
        return EjecucionActividad.construir(
                entidad.getIdentificador(),
                entidad.getFechaProgramada(),
                entidad.getFechaRealizacion(),
                entidad.getHoraInicio(),
                entidad.getHoraFin(),
                ejecucionActividadEstadoActividadMapeador.construirModelo(entidad.getEstadoActividad()),
                actividadMapeador.construirModelo(entidad.getActividad())
        );
    }

    public EjecucionActividadDTO construirDTO(EjecucionActividadEntidad entidad) {
        return new EjecucionActividadDTO(
                entidad.getIdentificador().toString(),
                entidad.getFechaProgramada().toString(),
                entidad.getFechaRealizacion() != null ? entidad.getFechaRealizacion().toString() : null,
                entidad.getHoraInicio() != null ? entidad.getHoraInicio().toString() : null,
                entidad.getHoraFin() != null ? entidad.getHoraFin().toString() : null,
                estadoActividadMapeador.construirDTO(entidad.getEstadoActividad().getEstadoActividad())
        );
    }

    public List<EjecucionActividadDTO> construirDTOs(List<EjecucionActividadEntidad> entidades) {
        return entidades.stream().map(this::construirDTO).toList();
    }

    public Page<EjecucionActividadDTO> construirDTOsPaginado(Page<EjecucionActividadEntidad> entidades) {
        var dtos = entidades.getContent().stream().map(this::construirDTO).toList();
        return new PageImpl<>(dtos, entidades.getPageable(), entidades.getTotalElements());
    }

    public void actualizarEntidad(EjecucionActividadEntidad entidad, EjecucionActividad dominio) {
        entidad.setFechaProgramada(dominio.getFechaProgramada());
        entidad.setFechaRealizacion(dominio.getFechaRealizacion());
        entidad.setHoraInicio(dominio.getHoraInicio());
        entidad.setHoraFin(dominio.getHoraFin());
        entidad.setEstadoActividad(ejecucionActividadEstadoActividadMapeador.construirEntidad(dominio.getEstado()));
    }

    public void actualizarTodos(UUID actividadId, List<EjecucionActividad> ejecucionesNuevas) {
        List<EjecucionActividadEntidad> ejecucionesActuales = ejecucionActividadDAO.findByActividadIdentificador(actividadId);
        Map<UUID, EjecucionActividadEntidad> mapaActual = ejecucionesActuales.stream()
                .collect(Collectors.toMap(EjecucionActividadEntidad::getIdentificador, Function.identity()));

        Set<UUID> idsNuevos = ejecucionesNuevas.stream()
                .map(EjecucionActividad::getIdentificador)
                .collect(Collectors.toSet());

        List<EjecucionActividadEntidad> paraEliminar = ejecucionesActuales.stream()
                .filter(entidad -> !idsNuevos.contains(entidad.getIdentificador()))
                .toList();

        List<EjecucionActividadEntidad> paraGuardar = ejecucionesNuevas.stream()
                .map(dominio -> {
                    EjecucionActividadEntidad entidad = mapaActual.getOrDefault(dominio.getIdentificador(), null);
                    if (entidad != null) {
                        actualizarEntidad(entidad, dominio);
                        return entidad;
                    } else {
                        return construirEntidad(dominio);
                    }
                })
                .collect(Collectors.toList());

        ejecucionActividadDAO.deleteAll(paraEliminar);
        ejecucionActividadDAO.saveAll(paraGuardar);
    }
}