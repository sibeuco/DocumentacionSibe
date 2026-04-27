package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.puerto.comando.DireccionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_DIRECCION_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class GuardarDireccionUseCase {
    private final DireccionRepositorioComando direccionRepositorioComando;
    private final DireccionRepositorioConsulta direccionRepositorioConsulta;

    public GuardarDireccionUseCase(DireccionRepositorioComando direccionRepositorioComando, DireccionRepositorioConsulta direccionRepositorioConsulta) {
        this.direccionRepositorioComando = direccionRepositorioComando;
        this.direccionRepositorioConsulta = direccionRepositorioConsulta;
    }

    public UUID ejecutar(Direccion direccion) {
        MotoresFabrica.MOTOR_DIRECCION.ejecutar(direccion, TipoOperacion.CREAR);

        validarNoExisteDireccionConNombre(direccion.getNombre());

        return this.direccionRepositorioComando.guardar(direccion);
    }

    private void validarNoExisteDireccionConNombre(String nombre) {
        if (!esNulo(this.direccionRepositorioConsulta.consultarPorNombre(nombre))){
            throw new ValorDuplicadoExcepcion(NOMBRE_DIRECCION_EXISTENTE);
        }
    }
}