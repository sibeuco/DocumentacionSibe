package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.EstadisticaMesDTO;
import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.enums.TipoArea;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ConsultarEstadisticasParticipantesPorMesUseCase {
    private final ActividadRepositorioConsulta actividadRepositorioConsulta;
    private final AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    public List<EstadisticaMesDTO> ejecutar(FiltroEstadisticaDTO filtro) {
        if (filtro.getIdArea() != null && filtro.getTipoArea() != null)
            validarAccesoPorTipoArea(filtro.getIdArea(), TipoArea.valueOf(filtro.getTipoArea().toUpperCase()));
        return actividadRepositorioConsulta.consultarEstadisticasParticipantesPorMes(filtro);
    }

    private void validarAccesoPorTipoArea(UUID area, TipoArea tipoArea) {
        switch (tipoArea) {
            case DIRECCION -> autorizacionServicio.validarAccesoADireccion(area);
            case AREA -> autorizacionServicio.validarAccesoAArea(area);
            case SUBAREA -> autorizacionServicio.validarAccesoASubarea(area);
        }
    }
}