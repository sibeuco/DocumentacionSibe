package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoIndicadorRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HayDatosTipoIndicadorManejador implements ManejadorRespuesta<Boolean> {
    private final TipoIndicadorRepositorioConsulta tipoIndicadorRepositorioConsulta;

    @Override
    public Boolean ejecutar() {
        return this.tipoIndicadorRepositorioConsulta.hayDatos();
    }
}