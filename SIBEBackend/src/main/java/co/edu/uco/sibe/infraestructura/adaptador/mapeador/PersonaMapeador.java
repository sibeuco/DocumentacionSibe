package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.PersonaDTO;
import co.edu.uco.sibe.dominio.modelo.Persona;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PersonaEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PersonaMapeador {
    private final IdentificacionMapeador identificacionMapeador;

    public PersonaDTO construirDTO(PersonaEntidad persona){
        var identificacion = this.identificacionMapeador.construirDTO(persona.getIdentificacion());

        return new PersonaDTO(persona.getIdentificador().toString(), persona.getNombres(), persona.getApellidos(), persona.getCorreo(), identificacion);
    }

    public PersonaEntidad construirEntidad(Persona persona){
        var identificacion = this.identificacionMapeador.construirEntidad(persona.getIdentificacion());

        return new PersonaEntidad(
                persona.getIdentificador(),
                persona.getNombres(),
                persona.getApellidos(),
                persona.getCorreo(),
                identificacion
        );
    }

    public void modificarEntidad(PersonaEntidad personaEntidad, Persona persona) {
        personaEntidad.setNombres(persona.getNombres());
        personaEntidad.setApellidos(persona.getApellidos());
        personaEntidad.setCorreo(persona.getCorreo());

        this.identificacionMapeador.modificarEntidad(personaEntidad.getIdentificacion(), persona.getIdentificacion());
    }

    public Persona construirModelo(PersonaEntidad entidad) {
        var identificacion = this.identificacionMapeador.construirModelo(entidad.getIdentificacion());

        return Persona.construir(entidad.getIdentificador(), entidad.getNombres(), entidad.getApellidos(), entidad.getCorreo(), identificacion);
    }
}