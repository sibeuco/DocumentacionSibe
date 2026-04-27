package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_EXISTE_TIPO_USUARIO_CON_CODIGO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarTipoUsuarioPorCodigoUseCase {
    private final TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    public ConsultarTipoUsuarioPorCodigoUseCase(TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta) {
        this.tipoUsuarioRepositorioConsulta = tipoUsuarioRepositorioConsulta;
    }

    public TipoUsuario ejecutar(String codigo) {
        validarSiNoExisteTipoUsuarioConCodigo(codigo);

        return this.tipoUsuarioRepositorioConsulta.consultarPorCodigo(codigo);
    }

    private void validarSiNoExisteTipoUsuarioConCodigo(String codigo) {
        if (esNulo(this.tipoUsuarioRepositorioConsulta.consultarPorCodigo(codigo))) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(NO_EXISTE_TIPO_USUARIO_CON_CODIGO, codigo));
        }
    }
}