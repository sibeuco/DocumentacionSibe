package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.AreaDetalladaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.AuthorizationException;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarAreaDetalladaUseCaseTest {

    @Mock
    private AreaRepositorioConsulta areaRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarAreaDetalladaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarAreaDetalladaUseCase(areaRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaConsultarAreaDetalladaCuandoTieneAcceso() {
        UUID areaId = UUID.randomUUID();
        AreaDetalladaDTO areaEsperada = new AreaDetalladaDTO();

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(areaRepositorioConsulta.consultarDetallePorIdentificador(areaId)).thenReturn(areaEsperada);

        AreaDetalladaDTO resultado = useCase.ejecutar(areaId);

        assertEquals(areaEsperada, resultado);
        verify(autorizacionServicio).validarAccesoAArea(areaId);
    }

    @Test
    void deberiaLanzarAuthorizationExceptionCuandoNoTieneAccesoAlArea() {
        UUID areaId = UUID.randomUUID();

        doThrow(new AuthorizationException("No tiene acceso")).when(autorizacionServicio).validarAccesoAArea(areaId);

        assertThrows(AuthorizationException.class, () -> useCase.ejecutar(areaId));

        verify(autorizacionServicio).validarAccesoAArea(areaId);
        verify(areaRepositorioConsulta, never()).consultarDetallePorIdentificador(any());
    }

    @Test
    void deberiaLanzarValorInvalidoExcepcionCuandoAreaNoExiste() {
        UUID areaId = UUID.randomUUID();

        doNothing().when(autorizacionServicio).validarAccesoAArea(areaId);
        when(areaRepositorioConsulta.consultarDetallePorIdentificador(areaId)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(areaId));
    }
}
