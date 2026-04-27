package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarAreaDetalladaUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarAreaDetalladaManejador implements ManejadorComandoRespuesta<String, AreaDetalladaDTO> {
    private final ConsultarAreaDetalladaUseCase consultarAreaDetalladaUseCase;

    @Override
    public AreaDetalladaDTO ejecutar(String comando) {
        return consultarAreaDetalladaUseCase.ejecutar(UtilUUID.textoAUUID(comando));
    }
}