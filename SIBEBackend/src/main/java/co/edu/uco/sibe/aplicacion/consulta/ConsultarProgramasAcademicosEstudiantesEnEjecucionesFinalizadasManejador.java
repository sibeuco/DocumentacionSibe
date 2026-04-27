package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasManejador implements ManejadorRespuesta<List<String>> {
    private final ConsultarProgramasAcademicosEstudiantesEnEjecucionesFinalizadasUseCase useCase;

    @Override
    public List<String> ejecutar() {
        return useCase.ejecutar();
    }
}