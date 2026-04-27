package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.EstadoActividadRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HayDatosEstadoActividadManejador implements ManejadorRespuesta<Boolean> {
    private final EstadoActividadRepositorioConsulta estadoActividadRepositorioConsulta;

    @Override
    public Boolean ejecutar() {
        return this.estadoActividadRepositorioConsulta.hayDatos();
    }
}