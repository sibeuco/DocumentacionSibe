package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PersonaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ActividadEntidad;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.ESPACIO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class ActividadMapeador {
    private final PersonaDAO personaDAO;
    private final IndicadorMapeador indicadorMapeador;

    public ActividadEntidad construirEntidad(Actividad actividad) {
        return new ActividadEntidad(
                actividad.getIdentificador(),
                actividad.getNombre(),
                actividad.getObjetivo(),
                actividad.getSemestre(),
                actividad.getRutaInsumos(),
                actividad.getFechaCreacion(),
                this.indicadorMapeador.construirEntidad(actividad.getIndicador()),
                actividad.getColaborador(),
                actividad.getCreador()
        );
    }

    public List<ActividadEntidad> construirEntidades(List<Actividad> actividades) {
        return actividades.stream().map(this::construirEntidad).toList();
    }

    public Actividad construirModelo(ActividadEntidad actividad) {
        return Actividad.construir(
                actividad.getIdentificador(),
                actividad.getNombre(),
                actividad.getObjetivo(),
                actividad.getSemestre(),
                actividad.getRutaInsumos(),
                actividad.getFechaCreacion(),
                this.indicadorMapeador.construriModelo(actividad.getIndicador()),
                actividad.getColaborador(),
                actividad.getCreador()
        );
    }

    public List<Actividad> construirModelos(List<ActividadEntidad> actividades) {
        return actividades.stream().map(this::construirModelo).toList();
    }

    public void actualizarEntidad(ActividadEntidad entidad, Actividad dominio) {
        entidad.setNombre(dominio.getNombre());
        entidad.setObjetivo(dominio.getObjetivo());
        entidad.setSemestre(dominio.getSemestre());
        entidad.setRutaInsumos(dominio.getRutaInsumos());
        entidad.setIndicador(indicadorMapeador.construirEntidad(dominio.getIndicador()));
        entidad.setColaborador(dominio.getColaborador());
        entidad.setCreador(dominio.getCreador());
    }

    public ActividadDTO construirDTO(ActividadEntidad actividad) {
        var persona = personaDAO.findById(actividad.getColaborador()).orElse(null);

        assert !esNulo(persona);
        return new ActividadDTO(
                actividad.getIdentificador().toString(),
                actividad.getNombre(),
                actividad.getObjetivo(),
                actividad.getSemestre(),
                actividad.getRutaInsumos(),
                actividad.getFechaCreacion().toString(),
                indicadorMapeador.construirDTO(actividad.getIndicador()),
                actividad.getColaborador().toString(),
                persona.getNombres() + ESPACIO + persona.getApellidos(),
                actividad.getCreador().toString()
        );
    }

    public List<ActividadDTO> construirDTOs(List<ActividadEntidad> actividades) {
        return actividades.stream().map(this::construirDTO).toList();
    }

    public Page<ActividadDTO> construirDTOsPaginado(Page<ActividadEntidad> entidades) {
        var dtos = entidades.getContent().stream().map(this::construirDTO).toList();
        return new PageImpl<>(dtos, entidades.getPageable(), entidades.getTotalElements());
    }
}