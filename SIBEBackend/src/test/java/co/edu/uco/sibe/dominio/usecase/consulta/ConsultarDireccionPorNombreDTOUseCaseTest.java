package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.DireccionDTO;
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
class ConsultarDireccionPorNombreDTOUseCaseTest {

    @Mock
    private DireccionRepositorioConsulta direccionRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarDireccionPorNombreDTOUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarDireccionPorNombreDTOUseCase(direccionRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarDireccionPorNombreDTOExitosamente() {
        UUID uuid = UUID.randomUUID();
        DireccionDTO dto = mock(DireccionDTO.class);
        when(dto.getIdentificador()).thenReturn(uuid.toString());
        when(direccionRepositorioConsulta.consultarPorNombreDTO("Direccion Prueba")).thenReturn(dto);
        doNothing().when(autorizacionServicio).validarAccesoADireccion(uuid);

        DireccionDTO resultado = useCase.ejecutar("Direccion Prueba");

        assertEquals(dto, resultado);
        verify(autorizacionServicio).validarAccesoADireccion(uuid);
    }

    @Test
    void deberiaLanzarExcepcionCuandoDireccionPorNombreNoExiste() {
        when(direccionRepositorioConsulta.consultarPorNombreDTO("NoExiste")).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("NoExiste"));
    }
}
