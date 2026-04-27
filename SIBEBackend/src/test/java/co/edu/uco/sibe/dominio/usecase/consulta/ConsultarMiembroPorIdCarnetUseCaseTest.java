package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.puerto.consulta.MiembroRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarMiembroPorIdCarnetUseCaseTest {

    @Mock
    private MiembroRepositorioConsulta miembroRepositorioConsulta;

    private ConsultarMiembroPorIdCarnetUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarMiembroPorIdCarnetUseCase(miembroRepositorioConsulta);
    }

    @Test
    void deberiaRetornarMiembroPorIdCarnetExitosamente() {
        MiembroDTO dto = mock(MiembroDTO.class);
        when(miembroRepositorioConsulta.consultarPorIdCarnetDTO("CARNET001")).thenReturn(dto);

        MiembroDTO resultado = useCase.ejecutar("CARNET001");

        assertEquals(dto, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoMiembroNoExistePorIdCarnet() {
        when(miembroRepositorioConsulta.consultarPorIdCarnetDTO("NOEXISTE")).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("NOEXISTE"));
    }
}
