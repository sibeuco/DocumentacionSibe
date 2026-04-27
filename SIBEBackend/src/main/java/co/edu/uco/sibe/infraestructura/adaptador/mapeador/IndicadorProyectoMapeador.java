package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.ProyectoDTO;
import co.edu.uco.sibe.dominio.modelo.Proyecto;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorProyectoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorProyectoEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class IndicadorProyectoMapeador {
    private final ProyectoMapeador proyectoMapeador;
    private final IndicadorProyectoDAO indicadorProyectoDAO;

    public IndicadorProyectoEntidad construirEntidad(Proyecto proyecto) {
        return new IndicadorProyectoEntidad(
                generar(uuid -> !esNulo(indicadorProyectoDAO.findById(uuid).orElse(null))),
                this.proyectoMapeador.construirEntidad(proyecto)
        );
    }

    public Proyecto construirModelo(IndicadorProyectoEntidad proyecto) {
        return this.proyectoMapeador.construriModelo(proyecto.getProyecto());
    }

    public void actualizarEntidad(IndicadorProyectoEntidad entidad, Proyecto proyecto) {
        entidad.setProyecto(this.proyectoMapeador.construirEntidad(proyecto));
    }

    public ProyectoDTO construirDTO(IndicadorProyectoEntidad proyecto) {
        return this.proyectoMapeador.construirDTO(proyecto.getProyecto());
    }
}