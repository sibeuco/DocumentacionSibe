package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EjecucionActividadEstadoActividadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEstadoActividadEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class EjecucionActividadEstadoActividadMapeador {

    private final EstadoActividadMapeador estadoActividadMapeador;
    private final EjecucionActividadEstadoActividadDAO dao;

    public EjecucionActividadEstadoActividadEntidad construirEntidad(EstadoActividad estadoActividad) {
        return new EjecucionActividadEstadoActividadEntidad(
                generar(uuid -> !esNulo(dao.findById(uuid).orElse(null))),
                estadoActividadMapeador.construirEntidad(estadoActividad)
        );
    }

    public EstadoActividad construirModelo(EjecucionActividadEstadoActividadEntidad entidad) {
        return estadoActividadMapeador.construirModelo(entidad.getEstadoActividad());
    }

    public void actualizarEntidad(EjecucionActividadEstadoActividadEntidad entidad, EstadoActividad dominio) {
        entidad.setEstadoActividad(estadoActividadMapeador.construirEntidad(dominio));
    }
}