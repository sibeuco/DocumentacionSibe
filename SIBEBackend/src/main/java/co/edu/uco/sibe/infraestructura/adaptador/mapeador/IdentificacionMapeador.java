package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.IdentificacionDTO;
import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IdentificacionTipoIdentificacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionTipoIdentificacionEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class IdentificacionMapeador {
    private final TipoIdentificacionMapeador tipoIdentificacionMapeador;
    private final IdentificacionTipoIdentificacionDAO identificacionTipoIdentificacionDAO;

    public void modificarEntidad(IdentificacionEntidad entidad, Identificacion modelo) {
        entidad.setNumeroIdentificacion(modelo.getNumeroIdentificacion());

        this.tipoIdentificacionMapeador.modificarEntidad(entidad.getTipoIdentificacion(), modelo.getTipoIdentificacion());
    }

    public Identificacion construirModelo(IdentificacionEntidad entidad) {
        var tipoIdentificacion = tipoIdentificacionMapeador.construirModelo(entidad.getTipoIdentificacion().getTipoIdentificacion());

        return Identificacion.construir(entidad.getIdentificador(), entidad.getNumeroIdentificacion(), tipoIdentificacion);
    }

    public IdentificacionEntidad construirEntidad(Identificacion identificacion) {
        var tipoIdentificacion = tipoIdentificacionMapeador.construirEntidad(identificacion.getTipoIdentificacion());
        var identificacionTipoIdentificacion = new IdentificacionTipoIdentificacionEntidad(
                generar(uuid -> !esNulo(identificacionTipoIdentificacionDAO.findById(uuid).orElse(null))),
                tipoIdentificacion
        );

        return new IdentificacionEntidad(identificacion.getIdentificador(), identificacion.getNumeroIdentificacion(), identificacionTipoIdentificacion);
    }

    public IdentificacionDTO construirDTO(IdentificacionEntidad identificacion) {
        var tipoIdentificacion = tipoIdentificacionMapeador.construirDTO(identificacion.getTipoIdentificacion().getTipoIdentificacion());

        return new IdentificacionDTO(identificacion.getIdentificador().toString(), identificacion.getNumeroIdentificacion(), tipoIdentificacion);
    }
}