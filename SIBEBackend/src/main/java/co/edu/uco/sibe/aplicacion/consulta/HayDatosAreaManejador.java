package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HayDatosAreaManejador implements ManejadorRespuesta<Boolean> {
    private final AreaRepositorioConsulta areaRepositorioConsulta;

    @Override
    public Boolean ejecutar() {
        return this.areaRepositorioConsulta.hayDatos();
    }
}