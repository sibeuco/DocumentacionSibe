package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorPublicoInteresDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorPublicoInteresEntidad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class IndicadorPublicoInteresMapeador {
    private final PublicoInteresMapeador publicoInteresMapeador;
    private final IndicadorPublicoInteresDAO indicadorPublicoInteresDAO;

    public IndicadorPublicoInteresEntidad construirEntidad(PublicoInteres publicoInteres) {
        return new IndicadorPublicoInteresEntidad(
                generar(uuid -> !esNulo(indicadorPublicoInteresDAO.findById(uuid).orElse(null))),
                this.publicoInteresMapeador.construirEntidad(publicoInteres)
        );
    }

    public PublicoInteres construirModelo(IndicadorPublicoInteresEntidad publicoInteres) {
        return publicoInteresMapeador.construirModelo(publicoInteres.getPublicoInteres());
    }

    public void actualizarEntidad(IndicadorPublicoInteresEntidad entidad, PublicoInteres publicoInteres) {
        entidad.setPublicoInteres(this.publicoInteresMapeador.construirEntidad(publicoInteres));
    }

    public PublicoInteresDTO construirDTO(IndicadorPublicoInteresEntidad publicoInteres) {
        return publicoInteresMapeador.construirDTO(publicoInteres.getPublicoInteres());
    }

    public List<IndicadorPublicoInteresEntidad> construirEntidades(List<PublicoInteres> publicosInteres) {
        return publicosInteres.stream().map(this::construirEntidad).toList();
    }

    public List<PublicoInteres> construirModelos(List<IndicadorPublicoInteresEntidad> publicosInteres) {
        return publicosInteres.stream().map(this::construirModelo).toList();
    }

    public List<PublicoInteresDTO> construirDTOs(List<IndicadorPublicoInteresEntidad> publicosInteres) {
        return publicosInteres.stream().map(this::construirDTO).toList();
    }

    public void actualizarEntidades(List<IndicadorPublicoInteresEntidad> entidades, List<PublicoInteres> publicosInteres) {
        Set<UUID> desiredActionIds = publicosInteres.stream()
                .map(PublicoInteres::getIdentificador)
                .collect(Collectors.toSet());

        Set<UUID> publicosInteresActualesId = entidades.stream()
                .map(pae -> pae.getPublicoInteres().getIdentificador())
                .collect(Collectors.toSet());

        entidades.removeIf(publicoInteres ->
                !desiredActionIds.contains(publicoInteres.getPublicoInteres().getIdentificador())
        );

        publicosInteres.stream()
                .filter(domainAccion -> !publicosInteresActualesId.contains(domainAccion.getIdentificador()))
                .forEach(domainAccion -> {
                    var nuevaEntidadUnion = this.construirEntidad(domainAccion);

                    entidades.add(nuevaEntidadUnion);
                });
    }
}