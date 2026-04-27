package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarDireccionDetalladaUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarDireccionDetalladaManejador implements ManejadorComandoRespuesta<String, DireccionDetalladaDTO> {
    private final ConsultarDireccionDetalladaUseCase consultarDireccionDetalladaUseCase;

    @Override
    public DireccionDetalladaDTO ejecutar(String comando) {
        return consultarDireccionDetalladaUseCase.ejecutar(UtilUUID.textoAUUID(comando));
    }
}