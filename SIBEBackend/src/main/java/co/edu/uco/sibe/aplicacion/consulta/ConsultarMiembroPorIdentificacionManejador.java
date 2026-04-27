package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarMiembroPorIdentificacionUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarMiembroPorIdentificacionManejador implements ManejadorComandoRespuesta<String, MiembroDTO> {
    private final ConsultarMiembroPorIdentificacionUseCase consultarMiembroPorIdentificacionUseCase;

    @Override
    public MiembroDTO ejecutar(String comando) {
        return consultarMiembroPorIdentificacionUseCase.ejecutar(comando);
    }
}