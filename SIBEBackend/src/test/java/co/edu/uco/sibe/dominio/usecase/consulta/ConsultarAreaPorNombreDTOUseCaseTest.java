package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.AreaDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.AreaRepositorioConsulta;
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
class ConsultarAreaPorNombreDTOUseCaseTest {

    @Mock
    private AreaRepositorioConsulta areaRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarAreaPorNombreDTOUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarAreaPorNombreDTOUseCase(areaRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarAreaPorNombreDTOExitosamente() {
        UUID uuid = UUID.randomUUID();
        AreaDTO dto = mock(AreaDTO.class);
        when(dto.getIdentificador()).thenReturn(uuid.toString());
        when(areaRepositorioConsulta.consultarPorNombreDTO("Area Prueba")).thenReturn(dto);
        doNothing().when(autorizacionServicio).validarAccesoAArea(uuid);

        AreaDTO resultado = useCase.ejecutar("Area Prueba");

        assertEquals(dto, resultado);
        verify(autorizacionServicio).validarAccesoAArea(uuid);
    }

    @Test
    void deberiaLanzarExcepcionCuandoAreaPorNombreNoExiste() {
        when(areaRepositorioConsulta.consultarPorNombreDTO("NoExiste")).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("NoExiste"));
    }
}
