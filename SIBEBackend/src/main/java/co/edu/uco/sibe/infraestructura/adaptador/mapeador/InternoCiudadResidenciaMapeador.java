package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.InternoCiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.InternoCiudadResidenciaEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class InternoCiudadResidenciaMapeador {
    private final CiudadResidenciaMapeador ciudadResidenciaMapeador;
    private final InternoCiudadResidenciaDAO internoCiudadResidenciaDAO;
    private final CiudadResidenciaDAO ciudadResidenciaDAO;

    public InternoCiudadResidenciaEntidad construirEntidad(CiudadResidencia ciudadResidencia) {
        var ciudadResidenciaEncontrada = this.ciudadResidenciaDAO.findFirstByDescripcion(ciudadResidencia.getDescripcion());

        return new InternoCiudadResidenciaEntidad(
                generar(uuid -> !esNulo(internoCiudadResidenciaDAO.findById(uuid).orElse(null))),
                esNulo(ciudadResidenciaEncontrada) ? ciudadResidenciaDAO.save(this.ciudadResidenciaMapeador.construirEntidad(ciudadResidencia)) : ciudadResidenciaEncontrada
        );
    }

    public CiudadResidencia construirModelo(InternoCiudadResidenciaEntidad internoCiudadResidenciaEntidad) {
        return ciudadResidenciaMapeador.construirModelo(internoCiudadResidenciaEntidad.getCiudadResidencia());
    }

    public void modificarEntidad(InternoCiudadResidenciaEntidad puente, CiudadResidencia ciudadResidencia) {
        var entidadCorrecta = this.ciudadResidenciaDAO.findFirstByDescripcion(ciudadResidencia.getDescripcion());
        if (esNulo(entidadCorrecta)) {
            entidadCorrecta = ciudadResidenciaDAO.save(this.ciudadResidenciaMapeador.construirEntidad(ciudadResidencia));
        }
        puente.setCiudadResidencia(entidadCorrecta);
    }
}