package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HayDatosSubareaManejador implements ManejadorRespuesta<Boolean> {
    private final SubareaRepositorioConsulta subareaRepositorioConsulta;

    @Override
    public Boolean ejecutar() {
        return this.subareaRepositorioConsulta.hayDatos();
    }
}