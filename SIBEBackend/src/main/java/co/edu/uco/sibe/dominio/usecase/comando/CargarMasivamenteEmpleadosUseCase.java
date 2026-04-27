package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.puerto.comando.EmpleadoRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.MiembroRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.EmpleadoRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class CargarMasivamenteEmpleadosUseCase {
    private final EmpleadoRepositorioComando empleadoRepositorioComando;
    private final EmpleadoRepositorioConsulta empleadoRepositorioConsulta;
    private final MiembroRepositorioComando miembroRepositorioComando;

    public CargarMasivamenteEmpleadosUseCase(EmpleadoRepositorioComando empleadoRepositorioComando,
                                              EmpleadoRepositorioConsulta empleadoRepositorioConsulta,
                                              MiembroRepositorioComando miembroRepositorioComando) {
        this.empleadoRepositorioComando = empleadoRepositorioComando;
        this.empleadoRepositorioConsulta = empleadoRepositorioConsulta;
        this.miembroRepositorioComando = miembroRepositorioComando;
    }

    public UUID ejecutar(Empleado empleado) {
        MotoresFabrica.MOTOR_CIUDAD_RESIDENCIA.ejecutar(empleado.getCiudadResidencia(), TipoOperacion.CREAR);
        MotoresFabrica.MOTOR_RELACION_LABORAL.ejecutar(empleado.getRelacionLaboral(), TipoOperacion.CREAR);
        MotoresFabrica.MOTOR_CENTRO_COSTOS.ejecutar(empleado.getCentroCostos(), TipoOperacion.CREAR);
        MotoresFabrica.MOTOR_EMPLEADO.ejecutar(empleado, TipoOperacion.CREAR);

        var empleadoActual = this.empleadoRepositorioConsulta.consultarPorIdentificacion(empleado.getNumeroIdentificacion());

        if (!esNulo(empleadoActual)) {
            miembroRepositorioComando.desactivarPorNumeroIdentificacion(empleado.getNumeroIdentificacion());
            return this.empleadoRepositorioComando.modificar(empleado, empleadoActual.getIdentificador());
        }

        miembroRepositorioComando.desactivarPorNumeroIdentificacion(empleado.getNumeroIdentificacion());

        return this.empleadoRepositorioComando.guardar(empleado);
    }
}