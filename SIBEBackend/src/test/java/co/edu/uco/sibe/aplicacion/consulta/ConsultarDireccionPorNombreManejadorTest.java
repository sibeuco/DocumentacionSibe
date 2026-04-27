package co.edu.uco.sibe.aplicacion.consulta;

import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.usecase.consulta.ConsultarDireccionPorNombreUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarDireccionPorNombreManejadorTest {

    @Mock private ConsultarDireccionPorNombreUseCase consultarDireccionPorNombreUseCase;

    private ConsultarDireccionPorNombreManejador manejador;

    @BeforeEach
    void setUp() {
        manejador = new ConsultarDireccionPorNombreManejador(consultarDireccionPorNombreUseCase);
    }

    @Test
    void deberiaConsultarDireccionPorNombre() {
        Direccion esperado = mock(Direccion.class);
        when(consultarDireccionPorNombreUseCase.ejecutar("DireccionTest")).thenReturn(esperado);

        Direccion resultado = manejador.ejecutar("DireccionTest");

        assertEquals(esperado, resultado);
        verify(consultarDireccionPorNombreUseCase).ejecutar("DireccionTest");
    }
}
