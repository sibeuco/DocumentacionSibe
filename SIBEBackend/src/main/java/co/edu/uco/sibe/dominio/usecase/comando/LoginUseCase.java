package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.servicio.EncriptarClaveServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.USUARIO_O_CLAVE_INCORRECTO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class LoginUseCase {
    private final PersonaRepositorioConsulta personaRepositorioConsulta;
    private final EncriptarClaveServicio encriptarClaveServicio;

    public LoginUseCase(PersonaRepositorioConsulta personaRepositorioConsulta, EncriptarClaveServicio encriptarClaveServicio) {
        this.personaRepositorioConsulta = personaRepositorioConsulta;
        this.encriptarClaveServicio = encriptarClaveServicio;
    }

    public UUID ejecutar(String correo) {
        validarSiNoExisteUsuarioConCorreo(correo);

        return personaRepositorioConsulta.consultarUsuarioPorCorreo(correo).getIdentificador();
    }

    private void validarSiNoExisteUsuarioConCorreo(String email) {
        if (esNulo(this.personaRepositorioConsulta.consultarUsuarioPorCorreo(email))) {
            throw new AuthorizationException(USUARIO_O_CLAVE_INCORRECTO);
        }
    }
}