package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.puerto.comando.EmpleadoRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CentroCostosDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EmpleadoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RelacionLaboralDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EmpleadoMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Repository
@AllArgsConstructor
public class EmpleadoRepositorioComandoImplementacion implements EmpleadoRepositorioComando {
    private final EmpleadoDAO empleadoDAO;
    private final CiudadResidenciaDAO ciudadResidenciaDAO;
    private final RelacionLaboralDAO relacionLaboralDAO;
    private final CentroCostosDAO centroCostosDAO;
    private final EmpleadoMapeador empleadoMapeador;

    @Override
    public UUID guardar(Empleado empleado) {
        var entidad = empleadoMapeador.construirEntidad(empleado);
        entidad.setEstaActivo(true);

        if (esNulo(this.ciudadResidenciaDAO.findById(entidad.getCiudadResidencia().getCiudadResidencia().getIdentificador()).orElse(null))) {
            this.ciudadResidenciaDAO.save(entidad.getCiudadResidencia().getCiudadResidencia());
        }

        if (esNulo(this.relacionLaboralDAO.findById(entidad.getRelacionLaboral().getRelacionLaboral().getIdentificador()).orElse(null))) {
            this.relacionLaboralDAO.save(entidad.getRelacionLaboral().getRelacionLaboral());
        }

        if (esNulo(this.centroCostosDAO.findById(entidad.getCentroCostos().getCentroCostos().getIdentificador()).orElse(null))) {
            this.centroCostosDAO.save(entidad.getCentroCostos().getCentroCostos());
        }

        return this.empleadoDAO.save(entidad).getIdentificador();
    }

    @Override
    public UUID modificar(Empleado empleado, UUID identificador) {
        var entidad = empleadoDAO.findById(identificador).orElse(null);

        assert !esNulo(entidad);
        empleadoMapeador.modificarEntidad(entidad, empleado);
        entidad.setEstaActivo(true);

        return this.empleadoDAO.save(entidad).getIdentificador();
    }

    @Override
    public void desactivarTodos() {
        this.empleadoDAO.desactivarTodos();
    }
}