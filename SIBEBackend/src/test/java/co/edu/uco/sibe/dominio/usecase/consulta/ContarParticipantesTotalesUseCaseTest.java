package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.FiltroEstadisticaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.testdatabuilder.FiltroEstadisticaDTOTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContarParticipantesTotalesUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;
    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ContarParticipantesTotalesUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ContarParticipantesTotalesUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarResultadoExitosamenteSinFiltroOrganizacional() {
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTOTestDataBuilder().construir();
        when(actividadRepositorioConsulta.contarParticipantesTotales(filtro)).thenReturn(100L);

        Long resultado = useCase.ejecutar(filtro);

        assertEquals(100L, resultado);
    }

    @Test
    void deberiaRetornarResultadoExitosamenteConFiltroDeArea() {
        UUID areaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTOTestDataBuilder()
                .conIdArea(areaId)
                .conTipoArea("AREA")
                .construir();
        when(actividadRepositorioConsulta.contarParticipantesTotales(filtro)).thenReturn(50L);

        Long resultado = useCase.ejecutar(filtro);

        assertEquals(50L, resultado);
        verify(autorizacionServicio).validarAccesoAArea(areaId);
    }

    @Test
    void deberiaRetornarResultadoExitosamenteConFiltroDeDireccion() {
        UUID direccionId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTOTestDataBuilder()
                .conIdArea(direccionId)
                .conTipoArea("DIRECCION")
                .construir();
        when(actividadRepositorioConsulta.contarParticipantesTotales(filtro)).thenReturn(30L);

        Long resultado = useCase.ejecutar(filtro);

        assertEquals(30L, resultado);
        verify(autorizacionServicio).validarAccesoADireccion(direccionId);
    }

    @Test
    void deberiaRetornarResultadoExitosamenteConFiltroDeSubarea() {
        UUID subareaId = UUID.randomUUID();
        FiltroEstadisticaDTO filtro = new FiltroEstadisticaDTOTestDataBuilder()
                .conIdArea(subareaId)
                .conTipoArea("SUBAREA")
                .construir();
        when(actividadRepositorioConsulta.contarParticipantesTotales(filtro)).thenReturn(20L);

        Long resultado = useCase.ejecutar(filtro);

        assertEquals(20L, resultado);
        verify(autorizacionServicio).validarAccesoASubarea(subareaId);
    }
}
