package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarAreaManejador implements ManejadorRespuesta<List<Area>> {
    private final AreaRepositorioConsulta areaRepositorioConsulta;

    @Override
    public List<Area> ejecutar() {
        return this.areaRepositorioConsulta.consultarTodos();
    }
}