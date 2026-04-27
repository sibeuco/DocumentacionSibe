package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.puerto.comando.TipoIdentificacionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.DESCRIPCION_TIPO_IDENTIFICACION_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.SIGLA_TIPO_IDENTIFICACION_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class GuardarTipoIdentificacionUseCase {
    private final TipoIdentificacionRepositorioComando tipoIdentificacionRepositorioComando;
    private final TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    public GuardarTipoIdentificacionUseCase(TipoIdentificacionRepositorioComando tipoIdentificacionRepositorioComando, TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta) {
        this.tipoIdentificacionRepositorioComando = tipoIdentificacionRepositorioComando;
        this.tipoIdentificacionRepositorioConsulta = tipoIdentificacionRepositorioConsulta;
    }

    public UUID ejecutar(TipoIdentificacion tipoIdentificacion) {
        MotoresFabrica.MOTOR_TIPO_IDENTIFICACION.ejecutar(tipoIdentificacion, TipoOperacion.CREAR);

        validarNoExisteTipoIdentificacionConSigla(tipoIdentificacion.getDescripcion());
        validarNoExisteTipoIdentificacionConDescripcion(tipoIdentificacion.getDescripcion());

        return this.tipoIdentificacionRepositorioComando.guardar(tipoIdentificacion);
    }

    private void validarNoExisteTipoIdentificacionConSigla(String sigla) {
        if (!esNulo(this.tipoIdentificacionRepositorioConsulta.consultarPorSigla(sigla))){
            throw new ValorDuplicadoExcepcion(SIGLA_TIPO_IDENTIFICACION_EXISTENTE);
        }
    }

    private void validarNoExisteTipoIdentificacionConDescripcion(String descripcion) {
        if (!esNulo(this.tipoIdentificacionRepositorioConsulta.consultarPorDescripcion(descripcion))){
            throw new ValorDuplicadoExcepcion(DESCRIPCION_TIPO_IDENTIFICACION_EXISTENTE);
        }
    }
}