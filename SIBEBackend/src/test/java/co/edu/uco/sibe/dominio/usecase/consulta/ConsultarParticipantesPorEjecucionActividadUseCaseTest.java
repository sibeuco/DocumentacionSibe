package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.ParticipanteDTO;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.testdatabuilder.EjecucionActividadTestDataBuilder;
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
class ConsultarParticipantesPorEjecucionActividadUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarParticipantesPorEjecucionActividadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarParticipantesPorEjecucionActividadUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaConsultarParticipantesPorEjecucionCuandoTieneAcceso() {
        UUID ejecucionId = UUID.randomUUID();
        EjecucionActividad ejecucion = new EjecucionActividadTestDataBuilder().conIdentificador(ejecucionId).construir();
        List<ParticipanteDTO> listaEsperada = List.of(new ParticipanteDTO());

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId)).thenReturn(ejecucion);
        when(actividadRepositorioConsulta.consultarParticipantesPorEjecucionActividad(ejecucionId)).thenReturn(listaEsperada);

        List<ParticipanteDTO> resultado = useCase.ejecutar(ejecucionId);

        assertEquals(listaEsperada, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoEjecucionNoExiste() {
        UUID ejecucionId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoAEjecucionActividad(ejecucionId);
        when(actividadRepositorioConsulta.consultarEjecucionActividadPorIdentificador(ejecucionId)).thenReturn(null);

        assertThrows(co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(ejecucionId));
    }
}
