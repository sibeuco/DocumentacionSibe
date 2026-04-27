package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIdentificacionRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HayDatosTipoIdentificacionManejador implements ManejadorRespuesta<Boolean> {
    private final TipoIdentificacionRepositorioConsulta tipoIdentificacionRepositorioConsulta;

    @Override
    public Boolean ejecutar() {
        return this.tipoIdentificacionRepositorioConsulta.hayDatos();
    }
}