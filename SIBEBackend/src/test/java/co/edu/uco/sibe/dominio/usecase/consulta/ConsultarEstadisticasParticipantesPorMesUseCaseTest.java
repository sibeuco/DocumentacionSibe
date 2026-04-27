package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.EstadisticaMesDTO;
import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.testdatabuilder.FiltroEstadisticaDTOTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarEstadisticasParticipantesPorMesUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;
    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarEstadisticasParticipantesPorMesUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarEstadisticasParticipantesPorMesUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarResultadoExitosamenteSinFiltroOrganizacional() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTOTestDataBuilder().construir();
        List<EstadisticaMesDTO> esperado = List.of(mock(EstadisticaMesDTO.class));
        when(actividadRepositorioConsulta.consultarEstadisticasParticipantesPorMes(filtro)).thenReturn(esperado);

        List<EstadisticaMesDTO> resultado = useCase.ejecutar(filtro);

        assertEquals(esperado, resultado);
    }

    @Test
    void deberiaRetornarResultadoExitosamenteConFiltroDeArea() {
        UUID areaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTOTestDataBuilder()
                .conIdArea(areaId)
                .conTipoArea("AREA")
                .construir();
        List<EstadisticaMesDTO> esperado = List.of(mock(EstadisticaMesDTO.class));
        when(actividadRepositorioConsulta.consultarEstadisticasParticipantesPorMes(filtro)).thenReturn(esperado);

        List<EstadisticaMesDTO> resultado = useCase.ejecutar(filtro);

        assertEquals(esperado, resultado);
        verify(autorizacionServicio).validarAccesoAArea(areaId);
    }

    @Test
    void deberiaRetornarResultadoExitosamenteConFiltroDeDireccion() {
        UUID direccionId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTOTestDataBuilder()
                .conIdArea(direccionId)
                .conTipoArea("DIRECCION")
                .construir();
        List<EstadisticaMesDTO> esperado = List.of(mock(EstadisticaMesDTO.class));
        when(actividadRepositorioConsulta.consultarEstadisticasParticipantesPorMes(filtro)).thenReturn(esperado);

        List<EstadisticaMesDTO> resultado = useCase.ejecutar(filtro);

        assertEquals(esperado, resultado);
        verify(autorizacionServicio).validarAccesoADireccion(direccionId);
    }

    @Test
    void deberiaRetornarResultadoExitosamenteConFiltroDeSubarea() {
        UUID subareaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTOTestDataBuilder()
                .conIdArea(subareaId)
                .conTipoArea("SUBAREA")
                .construir();
        List<EstadisticaMesDTO> esperado = List.of(mock(EstadisticaMesDTO.class));
        when(actividadRepositorioConsulta.consultarEstadisticasParticipantesPorMes(filtro)).thenReturn(esperado);

        List<EstadisticaMesDTO> resultado = useCase.ejecutar(filtro);

        assertEquals(esperado, resultado);
        verify(autorizacionServicio).validarAccesoASubarea(subareaId);
    }
}
