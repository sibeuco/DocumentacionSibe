package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.UsuarioDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_EXISTE_PERSONA_CON_IDENTIFICADOR;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ConsultarUsuarioPorIdentificadorUseCase {
    private final PersonaRepositorioConsulta personaRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public ConsultarUsuarioPorIdentificadorUseCase(PersonaRepositorioConsulta personaRepositorioConsulta,
            AutorizacionContextoOrganizacionalServicio autorizacionServicio) {
        this.personaRepositorioConsulta = personaRepositorioConsulta;
        this.autorizacionServicio = autorizacionServicio;
    }

    public UsuarioDTO ejecutar(UUID identificador) {
        autorizacionServicio.validarAccesoAUsuario(identificador);
        validarSiNoExisteUsuarioConId(identificador);

        return personaRepositorioConsulta.consultarUsuarioPorIdentificadorDTO(identificador);

    }

    private void validarSiNoExisteUsuarioConId(UUID identificador) {
        if (esNulo(this.personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador))) {
            throw new ValorInvalidoExcepcion(
                    obtenerMensajeConParametro(NO_EXISTE_PERSONA_CON_IDENTIFICADOR, identificador));
        }
    }
}