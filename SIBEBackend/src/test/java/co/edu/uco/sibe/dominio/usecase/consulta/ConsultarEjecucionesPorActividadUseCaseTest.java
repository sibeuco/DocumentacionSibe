package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.EjecucionActividadDTO;
import co.edu.uco.sibe.dominio.modelo.Actividad;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.testdatabuilder.ActividadTestDataBuilder;
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
class ConsultarEjecucionesPorActividadUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarEjecucionesPorActividadUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarEjecucionesPorActividadUseCase(actividadRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaConsultarEjecucionesPorActividadCuandoTieneAcceso() {
        UUID actividadId = UUID.randomUUID();
        Actividad actividad = new ActividadTestDataBuilder().conIdentificador(actividadId).construir();
        List<EjecucionActividadDTO> listaEsperada = List.of(new EjecucionActividadDTO());

        doNothing().when(autorizacionServicio).validarAccesoAActividad(actividadId);
        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(actividad);
        when(actividadRepositorioConsulta.consultarFechasProgramadasPorActividad(actividad)).thenReturn(listaEsperada);

        List<EjecucionActividadDTO> resultado = useCase.ejecutar(actividadId.toString());

        assertEquals(listaEsperada, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoActividadNoExiste() {
        UUID actividadId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoAActividad(actividadId);
        when(actividadRepositorioConsulta.consultarPorIdentificador(actividadId)).thenReturn(null);

        assertThrows(co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion.class,
                () -> useCase.ejecutar(actividadId.toString()));
    }
}
