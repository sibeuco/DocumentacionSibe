package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasManejador implements ManejadorRespuesta<List<String>> {
    private final ConsultarCentrosCostosEmpleadosEnEjecucionesFinalizadasUseCase useCase;

    @Override
    public List<String> ejecutar() {
        return useCase.ejecutar();
    }
}