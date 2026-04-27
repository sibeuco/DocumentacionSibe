package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarMiembroPorIdCarnetUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarMiembroPorIdCarnetManejador implements ManejadorComandoRespuesta<String, MiembroDTO> {
    private final ConsultarMiembroPorIdCarnetUseCase consultarMiembroPorIdCarnetUseCase;

    @Override
    public MiembroDTO ejecutar(String comando) {
        return consultarMiembroPorIdCarnetUseCase.ejecutar(comando);
    }
}