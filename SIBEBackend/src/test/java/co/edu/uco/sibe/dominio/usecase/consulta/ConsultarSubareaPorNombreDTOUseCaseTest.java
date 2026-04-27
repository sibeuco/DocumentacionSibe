package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.SubareaDTO;
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
class ConsultarSubareaPorNombreDTOUseCaseTest {

    @Mock
    private SubareaRepositorioConsulta subareaRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarSubareaPorNombreDTOUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarSubareaPorNombreDTOUseCase(subareaRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarSubareaPorNombreDTOExitosamente() {
        UUID uuid = UUID.randomUUID();
        SubareaDTO dto = mock(SubareaDTO.class);
        when(dto.getIdentificador()).thenReturn(uuid.toString());
        when(subareaRepositorioConsulta.consultarPorNombreDTO("Subarea Prueba")).thenReturn(dto);
        doNothing().when(autorizacionServicio).validarAccesoASubarea(uuid);

        SubareaDTO resultado = useCase.ejecutar("Subarea Prueba");

        assertEquals(dto, resultado);
        verify(autorizacionServicio).validarAccesoASubarea(uuid);
    }

    @Test
    void deberiaLanzarExcepcionCuandoSubareaPorNombreNoExiste() {
        when(subareaRepositorioConsulta.consultarPorNombreDTO("NoExiste")).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("NoExiste"));
    }
}
