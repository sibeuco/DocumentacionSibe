package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_EXISTE_USUARIO_CON_CORREO;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarUsuarioPorCorreoUseCase {
    private final PersonaRepositorioConsulta personaRepositorioConsulta;

    public ConsultarUsuarioPorCorreoUseCase(PersonaRepositorioConsulta personaRepositorioConsulta) {
        this.personaRepositorioConsulta = personaRepositorioConsulta;
    }

    public UsuarioDTO ejecutar(String correo) {
        validarSiNoExisteUsuarioConCorreo(correo);
        return personaRepositorioConsulta.consultarUsuarioPorCorreoDTO(correo);
    }

    private void validarSiNoExisteUsuarioConCorreo(String correo) {
        if (esNulo(this.personaRepositorioConsulta.consultarUsuarioPorCorreo(correo))) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(NO_EXISTE_USUARIO_CON_CORREO, correo));
        }
    }
}