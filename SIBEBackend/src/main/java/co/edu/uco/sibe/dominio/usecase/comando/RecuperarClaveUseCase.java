package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.regla.TipoOperacion;
import co.edu.uco.sibe.dominio.regla.fabrica.MotoresFabrica;
import co.edu.uco.sibe.dominio.regla.implementacion.UsuarioRegla;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_EXISTE_USUARIO_CON_CORREO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class RecuperarClaveUseCase {
    private final PersonaRepositorioComando personaRepositorioComando;
    private final PersonaRepositorioConsulta personaRepositorioConsulta;
    private final EncriptarClaveServicio encriptarClaveServicio;

    public RecuperarClaveUseCase(PersonaRepositorioComando personaRepositorioComando, PersonaRepositorioConsulta personaRepositorioConsulta, EncriptarClaveServicio encriptarClaveServicio) {
        this.personaRepositorioComando = personaRepositorioComando;
        this.personaRepositorioConsulta = personaRepositorioConsulta;
        this.encriptarClaveServicio = encriptarClaveServicio;
    }

    public UUID ejecutar(String correo, String clave) {
        validarSiNoExisteUsuarioConCorreo(correo);

        UsuarioRegla.obtenerInstancia().validarClave(clave);

        var usuario = this.personaRepositorioConsulta.consultarUsuarioPorCorreo(correo);

        usuario.actualizarClave(clave);

        MotoresFabrica.MOTOR_USUARIO.ejecutar(usuario, TipoOperacion.ACTUALIZAR);

        var claveCifrada = this.encriptarClaveServicio.ejecutar(clave);

        return this.personaRepositorioComando.modificarClaveConCorreo(claveCifrada, correo);
    }

    private void validarSiNoExisteUsuarioConCorreo(String correo) {
        if (esNulo(this.personaRepositorioConsulta.consultarUsuarioPorCorreo(correo))) {
            throw new NullPointerException(obtenerMensajeConParametro(NO_EXISTE_USUARIO_CON_CORREO, correo));
        }
    }
}