package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import lombok.AllArgsConstructor;
import java.util.List;

@AllArgsConstructor
public class ConsultarMesesEjecucionesFinalizadasUseCase {
    private final ActividadRepositorioConsulta actividadRepositorioConsulta;

    public List<String> ejecutar() {
        return actividadRepositorioConsulta.consultarMesesEjecucionesFinalizadas();
    }
}