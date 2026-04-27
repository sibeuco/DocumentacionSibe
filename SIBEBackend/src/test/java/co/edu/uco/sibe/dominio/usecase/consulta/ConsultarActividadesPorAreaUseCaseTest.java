package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.ActividadDTO;
import co.edu.uco.sibe.dominio.modelo.Area;
import co.edu.uco.sibe.dominio.puerto.consulta.ActividadRepositorioConsulta;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarActividadesPorAreaUseCaseTest {

    @Mock
    private ActividadRepositorioConsulta actividadRepositorioConsulta;

    @Mock
    private AreaRepositorioConsulta areaRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarActividadesPorAreaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarActividadesPorAreaUseCase(actividadRepositorioConsulta, areaRepositorioConsulta,
                autorizacionServicio);
    }

    @Test
    void deberiaConsultarActividadesPorAreaCuandoTieneAcceso() {
        UUID areaId = UUID.randomUUID();
        Area area = Area.construir(areaId, "Area Test", new ArrayList<>(), new ArrayList<>());
        List<ActividadDTO> actividadesEsperadas = List.of(new ActividadDTO());

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(areaRepositorioConsulta.consultarPorIdentificador(areaId)).thenReturn(area);
        when(actividadRepositorioConsulta.consultarPorArea(area)).thenReturn(actividadesEsperadas);

        List<ActividadDTO> resultado = useCase.ejecutar(areaId.toString());

        assertEquals(actividadesEsperadas, resultado);
        verify(autorizacionServicio).validarAccesoAArea(areaId);
        verify(areaRepositorioConsulta).consultarPorIdentificador(areaId);
        verify(actividadRepositorioConsulta).consultarPorArea(area);
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAccesoAlArea() {
        UUID areaId = UUID.randomUUID();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio).validarAccesoAArea(areaId);

        assertThrows(AuthorizationException.class, () -> useCase.ejecutar(areaId.toString()));

        verify(autorizacionServicio).validarAccesoAArea(areaId);
        verify(areaRepositorioConsulta, never()).consultarPorIdentificador(any());
    }

    @Test
    void deberiaLanzarValorInvalidoExcepcionCuandoAreaNoExiste() {
        UUID areaId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(areaRepositorioConsulta.consultarPorIdentificador(areaId)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(areaId.toString()));

        verify(autorizacionServicio).validarAccesoAArea(areaId);
    }

    @Test
    void deberiaValidarAccesoAntesDeConsultarArea() {
        UUID areaId = UUID.randomUUID();

        doThrow(new AuthorizationException("Sin acceso")).when(autorizacionServicio).validarAccesoAArea(areaId);

        assertThrows(AuthorizationException.class, () -> useCase.ejecutar(areaId.toString()));

        verify(actividadRepositorioConsulta, never()).consultarPorArea(any());
    }
}
