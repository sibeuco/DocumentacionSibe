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
class ConsultarMiembroPorIdentificacionUseCaseTest {

    @Mock
    private MiembroRepositorioConsulta miembroRepositorioConsulta;

    private ConsultarMiembroPorIdentificacionUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarMiembroPorIdentificacionUseCase(miembroRepositorioConsulta);
    }

    @Test
    void deberiaRetornarMiembroPorIdentificacionExitosamente() {
        MiembroDTO dto = mock(MiembroDTO.class);
        when(miembroRepositorioConsulta.consultarPorIdentificacionDTO("123456789")).thenReturn(dto);

        MiembroDTO resultado = useCase.ejecutar("123456789");

        assertEquals(dto, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoMiembroNoExistePorIdentificacion() {
        when(miembroRepositorioConsulta.consultarPorIdentificacionDTO("000000000")).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("000000000"));
    }
}
