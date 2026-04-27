package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarAreaPorNombreDTOUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarAreaPorNombreDTOManejador implements ManejadorComandoRespuesta<String, AreaDTO> {
    private final ConsultarAreaPorNombreDTOUseCase consultarAreaPorNombreDTOUseCase;

    @Override
    public AreaDTO ejecutar(String comando) {
        return consultarAreaPorNombreDTOUseCase.ejecutar(comando);
    }
}