package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.aplicacion.transversal.manejador.ManejadorRespuesta;
import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarIndicadoresParaActividadesUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ConsultarIndicadoresParaActividadesManejador implements ManejadorRespuesta<List<IndicadorDTO>> {
    private final ConsultarIndicadoresParaActividadesUseCase consultarIndicadoresParaActividadesUseCase;

    @Override
    public List<IndicadorDTO> ejecutar() {
        return this.consultarIndicadoresParaActividadesUseCase.ejecutar();
    }
}
