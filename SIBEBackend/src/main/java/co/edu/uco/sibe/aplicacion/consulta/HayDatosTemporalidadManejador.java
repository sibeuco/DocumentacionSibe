package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.TemporalidadRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HayDatosTemporalidadManejador implements ManejadorRespuesta<Boolean> {
    private final TemporalidadRepositorioConsulta temporalidadRepositorioConsulta;

    @Override
    public Boolean ejecutar() {
        return this.temporalidadRepositorioConsulta.hayDatos();
    }
}