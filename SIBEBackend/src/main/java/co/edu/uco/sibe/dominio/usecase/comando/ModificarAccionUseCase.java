package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Accion;
import co.edu.uco.sibe.dominio.puerto.comando.AccionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.AccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.DETALLE_EXISTENTE;

public class ModificarAccionUseCase {
    private final AccionRepositorioComando accionRepositorioComando;
    private final AccionRepositorioConsulta accionRepositorioConsulta;

    public ModificarAccionUseCase(AccionRepositorioComando accionRepositorioComando, AccionRepositorioConsulta accionRepositorioConsulta) {
        this.accionRepositorioComando = accionRepositorioComando;
        this.accionRepositorioConsulta = accionRepositorioConsulta;
    }

    public UUID ejecutar(Accion accion, UUID identificador){
        MotoresFabrica.MOTOR_ACCION.ejecutar(accion, TipoOperacion.ACTUALIZAR);

        validarNoExisteAccionConDetalle(accion.getDetalle(), identificador);

        return this.accionRepositorioComando.modificar(accion, identificador);
    }

    private void validarNoExisteAccionConDetalle(String detalle, UUID identificador) {
        var accion = this.accionRepositorioConsulta.consultarPorDetalle(detalle);
        if (!ValidadorObjeto.esNulo(accion) && !accion.getIdentificador().equals(identificador)){
            throw new ValorDuplicadoExcepcion(DETALLE_EXISTENTE);
        }
    }
}