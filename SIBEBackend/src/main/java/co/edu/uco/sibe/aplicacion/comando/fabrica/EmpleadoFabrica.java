package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.DatosEmpleadoComando;
import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.puerto.consulta.EmpleadoRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class EmpleadoFabrica {
    private final CiudadResidenciaFabrica ciudadResidenciaFabrica;
    private final RelacionLaboralFabrica relacionLaboralFabrica;
    private final CentroCostosFabrica centroCostosFabrica;
    private final EmpleadoRepositorioConsulta empleadoRepositorioConsulta;

    public Empleado construir(DatosEmpleadoComando empleadoComando) {
        return Empleado.construir(
                generar(uuid -> !esNulo(empleadoRepositorioConsulta.consultarPorIdentificador(uuid))),
                empleadoComando.getNombre(),
                empleadoComando.getIdentificacion(),
                ciudadResidenciaFabrica.construir(empleadoComando.getCiudadDeResidencia()),
                empleadoComando.getIdCarnet(),
                empleadoComando.getGenero(),
                relacionLaboralFabrica.construir(empleadoComando.getCodigoClasificacion(), empleadoComando.getGrupoRelacionLaboral()),
                centroCostosFabrica.construir(empleadoComando.getCodigoCentroCostos(), empleadoComando.getCentroCostos())
        );
    }
}