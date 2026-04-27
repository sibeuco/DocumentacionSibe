package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDetalladaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.SubareaRepositorioConsulta;
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
class ConsultarSubareaDetalladaUseCaseTest {

    @Mock
    private SubareaRepositorioConsulta subareaRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarSubareaDetalladaUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarSubareaDetalladaUseCase(subareaRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarSubareaDetalladaExitosamente() {
        UUID id = UUID.randomUUID();
        SubareaDetalladaDTO dto = mock(SubareaDetalladaDTO.class);
        doNothing().when(autorizacionServicio).validarAccesoASubarea(id);
        when(subareaRepositorioConsulta.consultarDetallePorIdentificador(id)).thenReturn(dto);

        SubareaDetalladaDTO resultado = useCase.ejecutar(id);

        assertEquals(dto, resultado);
        verify(autorizacionServicio).validarAccesoASubarea(id);
    }

    @Test
    void deberiaLanzarExcepcionCuandoSubareaDetalladaNoExiste() {
        UUID id = UUID.randomUUID();
        doNothing().when(autorizacionServicio).validarAccesoASubarea(id);
        when(subareaRepositorioConsulta.consultarDetallePorIdentificador(id)).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar(id));
    }
}
