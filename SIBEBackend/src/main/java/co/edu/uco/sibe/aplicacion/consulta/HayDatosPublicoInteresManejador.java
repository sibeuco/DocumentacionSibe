package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.PublicoInteresRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HayDatosPublicoInteresManejador  implements ManejadorRespuesta<Boolean> {
    private final PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;

    @Override
    public Boolean ejecutar() {
        return this.publicoInteresRepositorioConsulta.hayDatos();
    }
}