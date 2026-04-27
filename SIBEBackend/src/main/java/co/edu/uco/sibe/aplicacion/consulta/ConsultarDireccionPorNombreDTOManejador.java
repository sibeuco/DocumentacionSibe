package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.DireccionDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarDireccionPorNombreDTOUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarDireccionPorNombreDTOManejador implements ManejadorComandoRespuesta<String, DireccionDTO> {
    private final ConsultarDireccionPorNombreDTOUseCase consultarDireccionPorNombreDTOUseCase;

    @Override
    public DireccionDTO ejecutar(String comando) {
        return consultarDireccionPorNombreDTOUseCase.ejecutar(comando);
    }
}