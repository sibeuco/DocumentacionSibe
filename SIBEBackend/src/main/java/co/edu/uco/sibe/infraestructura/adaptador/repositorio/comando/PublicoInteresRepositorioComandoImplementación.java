package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.dominio.puerto.comando.PublicoInteresRepositorioComando;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PublicoInteresDAO;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.PublicoInteresMapeador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class PublicoInteresRepositorioComandoImplementación implements PublicoInteresRepositorioComando {
    private final PublicoInteresDAO publicoInteresDAO;
    private final PublicoInteresMapeador publicoInteresMapeador;

    @Override
    public UUID guardar(PublicoInteres publicoInteres) {
        var entidad = publicoInteresMapeador.construirEntidad(publicoInteres);

        return this.publicoInteresDAO.save(entidad).getIdentificador();
    }
}