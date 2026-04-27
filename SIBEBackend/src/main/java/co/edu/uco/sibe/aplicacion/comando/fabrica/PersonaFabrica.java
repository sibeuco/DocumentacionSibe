package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.aplicacion.comando.UsuarioComando;
import co.edu.uco.sibe.aplicacion.comando.UsuarioModificacionComando;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.dominio.puerto.consulta.PersonaRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.UUID;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class PersonaFabrica {
    private final IdentificacionFabrica identificacionFabrica;
    private final PersonaRepositorioConsulta personaRepositorioConsulta;

    public Persona construir(UsuarioComando comando){
        var identificadorPersona = generar(uuid -> !esNulo(personaRepositorioConsulta.consultarPersonaPorIdentificador(uuid)));
        var identificacion = this.identificacionFabrica.construir(comando.getNumeroIdentificacion(), comando.getTipoIdentificacion());
        var persona = Persona.construir(identificadorPersona, comando.getNombres(), comando.getApellidos(), comando.getCorreo(), identificacion);

        return persona;
    }

    public Persona construirActualizar(UsuarioModificacionComando comando, UUID identificador){
        var identificacionIdentificador = personaRepositorioConsulta.consultarPersonaPorIdentificador(identificador).getIdentificacion().getIdentificador();
        var identificacion = this.identificacionFabrica.construirActualizar(comando.getNumeroIdentificacion(), comando.getTipoIdentificacion(), identificacionIdentificador);
        var persona = Persona.construir(identificador, comando.getNombres(), comando.getApellidos(), comando.getCorreo(), identificacion);

        return persona;
    }
}