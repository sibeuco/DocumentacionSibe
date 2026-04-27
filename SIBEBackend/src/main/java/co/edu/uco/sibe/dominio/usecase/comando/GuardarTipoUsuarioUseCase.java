package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.dominio.puerto.comando.TipoUsuarioRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.CODIGO_TIPO_USUARIO_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_TIPO_USUARIO_EXISTENTE;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class GuardarTipoUsuarioUseCase {
    private final TipoUsuarioRepositorioComando tipoUsuarioRepositorioComando;
    private final TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    public GuardarTipoUsuarioUseCase(TipoUsuarioRepositorioComando tipoUsuarioRepositorioComando, TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta) {
        this.tipoUsuarioRepositorioComando = tipoUsuarioRepositorioComando;
        this.tipoUsuarioRepositorioConsulta = tipoUsuarioRepositorioConsulta;
    }

    public UUID ejecutar(TipoUsuario tipoUsuario) {
        MotoresFabrica.MOTOR_TIPO_USUARIO.ejecutar(tipoUsuario, TipoOperacion.CREAR);

        validarNoExisteTipoUsuarioConCodigo(tipoUsuario.getCodigo());
        validarNoExisteTipoUsuariConNombre(tipoUsuario.getNombre());

        return this.tipoUsuarioRepositorioComando.guardar(tipoUsuario);
    }

    private void validarNoExisteTipoUsuarioConCodigo(String codigo) {
        if (!esNulo(this.tipoUsuarioRepositorioConsulta.consultarPorCodigo(codigo))){
            throw new ValorDuplicadoExcepcion(CODIGO_TIPO_USUARIO_EXISTENTE);
        }
    }

    private void validarNoExisteTipoUsuariConNombre(String nombre) {
        if (!esNulo(this.tipoUsuarioRepositorioConsulta.consultarPorNombre(nombre))){
            throw new ValorDuplicadoExcepcion(NOMBRE_TIPO_USUARIO_EXISTENTE);
        }
    }
}