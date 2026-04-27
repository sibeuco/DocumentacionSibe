package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.SubareaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarSubareaPorNombreDTOUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarSubareaPorNombreDTOManejador implements ManejadorComandoRespuesta<String, SubareaDTO> {
    private final ConsultarSubareaPorNombreDTOUseCase consultarSubareaPorNombreDTOUseCase;

    @Override
    public SubareaDTO ejecutar(String comando) {
        return consultarSubareaPorNombreDTOUseCase.ejecutar(comando);
    }
}