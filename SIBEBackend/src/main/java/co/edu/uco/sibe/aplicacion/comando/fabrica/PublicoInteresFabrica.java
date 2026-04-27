package co.edu.uco.sibe.aplicacion.comando.fabrica;

import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.dominio.puerto.consulta.PublicoInteresRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID.generar;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;

@Component
@AllArgsConstructor
public class PublicoInteresFabrica {
    private final PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;

    public PublicoInteres construir(String comando) {
        return PublicoInteres.construir(
                generar(uuid -> !esNulo(publicoInteresRepositorioConsulta.consultarPorIdentificador(uuid))),
                comando
        );
    }
}
