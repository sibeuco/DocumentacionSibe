package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorComandoRespuesta;
import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
import co.edu.uco.sibe.dominio.transversal.utilitarios.UtilUUID;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarSubareaDetalladaUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultarSubareaDetalladaManejador implements ManejadorComandoRespuesta<String, SubareaDetalladaDTO> {
    private final ConsultarSubareaDetalladaUseCase consultarSubareaDetalladaUseCase;

    @Override
    public SubareaDetalladaDTO ejecutar(String comando) {
        return consultarSubareaDetalladaUseCase.ejecutar(UtilUUID.textoAUUID(comando));
    }
}