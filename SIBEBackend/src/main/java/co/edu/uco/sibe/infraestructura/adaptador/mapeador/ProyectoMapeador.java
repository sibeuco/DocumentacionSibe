package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ProyectoEntidad;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ProyectoMapeador {
    private ProyectoAccionMapeador proyectoAccionMapeador;

    public ProyectoEntidad construirEntidad(Proyecto proyecto) {
        return new ProyectoEntidad(
                proyecto.getIdentificador(),
                proyecto.getNumeroProyecto(),
                proyecto.getNombre(), proyecto.getObjetivo(),
                proyectoAccionMapeador.construirEntidades(proyecto.getAcciones())
        );
    }

    public Proyecto construriModelo(ProyectoEntidad proyecto) {
        return Proyecto.construir(
                proyecto.getIdentificador(),
                proyecto.getNumeroProyecto(),
                proyecto.getNombre(),
                proyecto.getObjetivo(),
                proyectoAccionMapeador.construirModelos(proyecto.getAcciones())
        );
    }

    public ProyectoDTO construirDTO(ProyectoEntidad proyecto) {
        return new ProyectoDTO(
                proyecto.getIdentificador().toString(),
                proyecto.getNumeroProyecto(),
                proyecto.getNombre(),
                proyecto.getObjetivo(),
                proyecto.getAcciones() != null
                    ? proyecto.getAcciones().stream()
                        .map(pa -> new co.edu.uco.sibe.dominio.dto.AccionDTO(
                                pa.getAccion().getIdentificador().toString(),
                                pa.getAccion().getDetalle(),
                                pa.getAccion().getObjetivo()))
                        .toList()
                    : java.util.List.of()
        );
    }

    public List<ProyectoDTO> construirDTOs(List<ProyectoEntidad> proyectos){
        return proyectos.stream().map(this::construirDTO).toList();
    }

    public Page<ProyectoDTO> construirDTOsPaginado(Page<ProyectoEntidad> entidades) {
        var dtos = entidades.getContent().stream()
                .map(this::construirDTO)
                .toList();
        return new PageImpl<>(dtos, entidades.getPageable(), entidades.getTotalElements());
    }

    public void actualizarEntidad(ProyectoEntidad entidad, Proyecto proyecto) {
        entidad.setNumeroProyecto(proyecto.getNumeroProyecto());
        entidad.setNombre(proyecto.getNombre());
        entidad.setObjetivo(proyecto.getObjetivo());
        proyectoAccionMapeador.actualizarTodos(entidad.getAcciones(), proyecto.getAcciones());
    }
}