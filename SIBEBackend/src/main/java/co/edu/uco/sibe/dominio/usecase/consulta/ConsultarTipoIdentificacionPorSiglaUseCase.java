package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_EXISTE_IDENTIFICACION_CON_LA_SIGLA;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarTipoIdentificacionPorSiglaUseCase {
    private final TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    public ConsultarTipoIdentificacionPorSiglaUseCase(TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta) {
        this.tipoIdentificacionRepositorioConsulta = tipoIdentificacionRepositorioConsulta;
    }

    public TipoIdentificacion ejecutar(String nombre) {
        validarSiNoExisteTipoIdentificacionConSigla(nombre);

        return this.tipoIdentificacionRepositorioConsulta.consultarPorSigla(nombre);
    }

    private void validarSiNoExisteTipoIdentificacionConSigla(String sigla) {
        if (esNulo(this.tipoIdentificacionRepositorioConsulta.consultarPorSigla(sigla))) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(NO_EXISTE_IDENTIFICACION_CON_LA_SIGLA, sigla));
        }
    }
}