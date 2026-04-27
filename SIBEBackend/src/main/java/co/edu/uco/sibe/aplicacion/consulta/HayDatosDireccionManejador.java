package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HayDatosDireccionManejador implements ManejadorRespuesta<Boolean> {
    private final DireccionRepositorioConsulta direccionRepositorioConsulta;

    @Override
    public Boolean ejecutar() {
        return this.direccionRepositorioConsulta.hayDatos();
    }
}