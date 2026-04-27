package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.puerto.comando.PersonaRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NO_EXISTE_PERSONA_CON_IDENTIFICADOR;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.obtenerMensajeConParametro;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

public class ModificarContrasenaUseCase {
    private final PersonaRepositorioComando personaRepositorioComando;
    private final PersonaRepositorioConsulta personaRepositorioConsulta;

    public ModificarContrasenaUseCase(PersonaRepositorioComando personaRepositorioComando, PersonaRepositorioConsulta personaRepositorioConsulta) {
        this.personaRepositorioComando = personaRepositorioComando;
        this.personaRepositorioConsulta = personaRepositorioConsulta;
    }

    public UUID ejecutar(String contrasena, UUID identificador){
        validarSiNoExistePersonaConId(identificador);

        return this.personaRepositorioComando.modificarClave(contrasena, identificador);
    }

    private void validarSiNoExistePersonaConId(UUID identificador) {
        if (esNulo(this.personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador))) {
            throw new ValorInvalidoExcepcion(obtenerMensajeConParametro(NO_EXISTE_PERSONA_CON_IDENTIFICADOR, identificador));
        }
    }
}