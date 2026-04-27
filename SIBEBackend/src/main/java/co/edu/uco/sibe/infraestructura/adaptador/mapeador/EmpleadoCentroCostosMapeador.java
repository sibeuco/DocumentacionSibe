package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CentroCostosDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EmpleadoCentroCostosDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EmpleadoCentroCostosEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class EmpleadoCentroCostosMapeador {
    private final CentroCostosMapeador centroCostosMapeador;
    private final EmpleadoCentroCostosDAO empleadoCentroCostosDAO;
    private final CentroCostosDAO centroCostosDAO;

    public EmpleadoCentroCostosEntidad construirEntidad(CentroCostos centroCostos) {
        var centroCostosEncontrado = centroCostosDAO.findFirstByCodigo(centroCostos.getCodigo());

        return new EmpleadoCentroCostosEntidad(
                generar(uuid -> !esNulo(empleadoCentroCostosDAO.findById(uuid).orElse(null))),
                esNulo(centroCostosEncontrado) ? centroCostosDAO.save(this.centroCostosMapeador.construirEntidad(centroCostos)) : centroCostosEncontrado
        );
    }

    public CentroCostos construirModelo(EmpleadoCentroCostosEntidad empleadoCentroCostosEntidad) {
        return centroCostosMapeador.construirModelo(empleadoCentroCostosEntidad.getCentroCostos());
    }

    public void modificarEntidad(EmpleadoCentroCostosEntidad puente, CentroCostos centroCostos) {
        var entidadCorrecta = centroCostosDAO.findFirstByCodigo(centroCostos.getCodigo());
        if (esNulo(entidadCorrecta)) {
            entidadCorrecta = centroCostosDAO.save(this.centroCostosMapeador.construirEntidad(centroCostos));
        }
        puente.setCentroCostos(entidadCorrecta);
    }
}