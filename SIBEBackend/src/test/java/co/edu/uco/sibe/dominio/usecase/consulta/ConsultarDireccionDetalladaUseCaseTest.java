package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.DireccionDetalladaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarDireccionDetalladaUseCaseTest {

    @Mock
    private DireccionRepositorioConsulta direccionRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarDireccionDetalladaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarDireccionDetalladaUseCase(direccionRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarDireccionDetalladaExitosamente() {
        UUID id = UUID.randomUUID();
        DireccionDetalladaDTO dto = mock(DireccionDetalladaDTO.class);
        doNothing().when(autorizacionServicio).validarAccesoADireccion(id);
        when(direccionRepositorioConsulta.consultarDetallePorIdentificador(id)).thenReturn(dto);

        DireccionDetalladaDTO resultado = useCase.ejecutar(id);

        assertEquals(dto, resultado);
        verify(autorizacionServicio).validarAccesoADireccion(id);
    }

    @Test
    void deberiaLanzarExcepcionCuandoDireccionDetalladaNoExiste() {
        UUID id = UUID.randomUUID();
        doNothing().when(autorizacionServicio).validarAccesoADireccion(id);
        when(direccionRepositorioConsulta.consultarDetallePorIdentificador(id)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(id));
    }
}
