package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.dominio.puerto.comando.TemporalidadRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.TemporalidadRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_TEMPORALIDAD_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class GuardarTemporalidadUseCase {
    private final TemporalidadRepositorioComando temporalidadRepositorioComando;
    private final TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;

    public GuardarTemporalidadUseCase(TemporalidadRepositorioComando temporalidadRepositorioComando, TemporalidadRepositorioConsulta temporalidadRepositorioConsulta) {
        this.temporalidadRepositorioComando = temporalidadRepositorioComando;
        this.temporalidadRepositorioConsulta = temporalidadRepositorioConsulta;
    }

    public UUID ejecutar(Temporalidad temporalidad) {
        MotoresFabrica.MOTOR_TEMPORALIDAD.ejecutar(temporalidad, TipoOperacion.CREAR);

        validarNoExisteTemporalidadConNombre(temporalidad.getNombre());

        return this.temporalidadRepositorioComando.guardar(temporalidad);
    }

    private void validarNoExisteTemporalidadConNombre(String nombre) {
        if (!esNulo(this.temporalidadRepositorioConsulta.consultarPorNombre(nombre))){
            throw new ValorDuplicadoExcepcion(NOMBRE_TEMPORALIDAD_EXISTENTE);
        }
    }
}