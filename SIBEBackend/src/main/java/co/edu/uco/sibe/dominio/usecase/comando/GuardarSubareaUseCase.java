package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Subarea;
import co.edu.uco.sibe.dominio.puerto.comando.SubareaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_SUB_AREA_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class GuardarSubareaUseCase {
    private final SubareaRepositorioComando subareaRepositorioComando;
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;

    public GuardarSubareaUseCase(SubareaRepositorioComando subareaRepositorioComando, SubareaRepositorioConsulta subareaRepositorioConsulta) {
        this.subareaRepositorioComando = subareaRepositorioComando;
        this.subareaRepositorioConsulta = subareaRepositorioConsulta;
    }

    public UUID ejecutar(Subarea subarea) {
        MotoresFabrica.MOTOR_SUBAREA.ejecutar(subarea, TipoOperacion.CREAR);

        validarNoExisteSubareaConNombre(subarea.getNombre());

        return this.subareaRepositorioComando.guardar(subarea);
    }

    private void validarNoExisteSubareaConNombre(String nombre) {
        if (!esNulo(this.subareaRepositorioConsulta.consultarPorNombre(nombre))){
            throw new ValorDuplicadoExcepcion(NOMBRE_SUB_AREA_EXISTENTE);
        }
    }
}