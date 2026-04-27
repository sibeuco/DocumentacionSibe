package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ActividadDetalladaDTO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PersonaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.ESPACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class ActividadDetalladaMapeador {
    private final IndicadorMapeador indicadorMapeador;
    private final EjecucionActividadDAO ejecucionActividadDAO;
    private final EjecucionActividadDetalladaMapeador ejecucionActividadDetalladaMapeador;
    private final PersonaDAO personaDAO;

    public ActividadDetalladaDTO construirDTO(ActividadEntidad entidad) {
        var ejecucionesEntidad = ejecucionActividadDAO.findByActividadIdentificador(entidad.getIdentificador());
        var ejecucionesDTO = ejecucionActividadDetalladaMapeador.construirDTOs(ejecucionesEntidad);
        var colaborador = personaDAO.findById(entidad.getColaborador()).orElse(null);

        assert !esNulo(colaborador);
        return new ActividadDetalladaDTO(
                entidad.getIdentificador().toString(),
                entidad.getNombre(),
                entidad.getObjetivo(),
                entidad.getSemestre(),
                entidad.getFechaCreacion().toString(),
                indicadorMapeador.construirDTO(entidad.getIndicador()),
                colaborador.getNombres() + ESPACIO + colaborador.getApellidos(),
                ejecucionesDTO
        );
    }

    public List<ActividadDetalladaDTO> construirDTOs(List<ActividadEntidad> entidades) {
        return entidades.stream().map(this::construirDTO).toList();
    }
}