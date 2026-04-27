package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.EstadoActividad;
import co.edu.uco.sibe.dominio.puerto.comando.EstadoActividadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_ESTADO_ACTIVIDAD_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class GuardarEstadoActividadUseCase {
    private final EstadoActividadRepositorioComando estadoActividadRepositorioComando;
    private final EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;

    public GuardarEstadoActividadUseCase(EstadoActividadRepositorioComando estadoActividadRepositorioComando, EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta) {
        this.estadoActividadRepositorioComando = estadoActividadRepositorioComando;
        this.estadoActividadRepositorioConsulta = estadoActividadRepositorioConsulta;
    }

    public UUID ejecutar(EstadoActividad estadoActividad) {
        MotoresFabrica.MOTOR_ESTADO_ACTIVIDAD.ejecutar(estadoActividad, TipoOperacion.CREAR);

        validarNoExisteEstadoActividadConNombre(estadoActividad.getNombre());

        return this.estadoActividadRepositorioComando.guardar(estadoActividad);
    }

    private void validarNoExisteEstadoActividadConNombre(String nombre) {
        if (!esNulo(this.estadoActividadRepositorioConsulta.consultarPorNombre(nombre))){
            throw new ValorDuplicadoExcepcion(NOMBRE_ESTADO_ACTIVIDAD_EXISTENTE);
        }
    }
}