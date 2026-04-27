package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.IndicadorDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.IndicadorRepositorioConsulta;

import java.util.List;

public class ConsultarIndicadoresParaActividadesUseCase {

    private final IndicadorRepositorioConsulta indicadorRepositorioConsulta;

    public ConsultarIndicadoresParaActividadesUseCase(IndicadorRepositorioConsulta indicadorRepositorioConsulta) {
        this.indicadorRepositorioConsulta = indicadorRepositorioConsulta;
    }

    public List<IndicadorDTO> ejecutar() {
        return indicadorRepositorioConsulta.consultarDTOsParaActividades();
    }
}
