package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.puerto.consulta.TipoUsuarioRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HayDatosTipoUsuarioManejador implements ManejadorRespuesta<Boolean> {
    private final TipoUsuarioRepositorioConsulta tipoUsuarioRepositorioConsulta;

    @Override
    public Boolean ejecutar() {
        return this.tipoUsuarioRepositorioConsulta.hayDatos();
    }
}