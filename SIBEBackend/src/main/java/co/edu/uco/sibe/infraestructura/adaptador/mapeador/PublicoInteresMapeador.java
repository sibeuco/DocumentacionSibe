package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PublicoInteresEntidad;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PublicoInteresMapeador {
    public PublicoInteresEntidad construirEntidad(PublicoInteres publicoInteres) {
        return new PublicoInteresEntidad(publicoInteres.getIdentificador(), publicoInteres.getNombre());
    }

    public PublicoInteres construirModelo(PublicoInteresEntidad publicoInteres) {
        return PublicoInteres.construir(publicoInteres.getIdentificador(), publicoInteres.getNombre());
    }

    public PublicoInteresDTO construirDTO(PublicoInteresEntidad publicoInteres) {
        return new PublicoInteresDTO(publicoInteres.getIdentificador().toString(), publicoInteres.getNombre());
    }

    public List<PublicoInteresDTO> construirDTOs(List<PublicoInteresEntidad> entidades) {
        return entidades.stream().map(this::construirDTO).toList();
    }

    public List<PublicoInteres> construirModelos(List<PublicoInteresEntidad> entidades) {
        return entidades.stream().map(this::construirModelo).toList();
    }
}