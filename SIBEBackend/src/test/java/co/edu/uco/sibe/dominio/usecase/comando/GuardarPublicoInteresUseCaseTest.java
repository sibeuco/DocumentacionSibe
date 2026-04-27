package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.dominio.puerto.comando.PublicoInteresRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.PublicoInteresRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.PUBLICO_INTERES_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarPublicoInteresUseCaseTest {

    @Mock
    private PublicoInteresRepositorioComando publicoInteresRepositorioComando;
    @Mock
    private PublicoInteresRepositorioConsulta publicoInteresRepositorioConsulta;

    private GuardarPublicoInteresUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarPublicoInteresUseCase(publicoInteresRepositorioComando, publicoInteresRepositorioConsulta);
    }

    @Test
    void deberiaGuardarPublicoInteresExitosamente() {
        UUID identificador = UUID.randomUUID();
        PublicoInteres publicoInteres = PublicoInteres.construir(UUID.randomUUID(), "Estudiantes");

        when(publicoInteresRepositorioConsulta.consultarPorNombre("Estudiantes")).thenReturn(null);
        when(publicoInteresRepositorioComando.guardar(publicoInteres)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(publicoInteres);

        assertEquals(identificador, resultado);
        verify(publicoInteresRepositorioComando).guardar(publicoInteres);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExiste() {
        PublicoInteres publicoInteres = PublicoInteres.construir(UUID.randomUUID(), "Estudiantes");

        when(publicoInteresRepositorioConsulta.consultarPorNombre("Estudiantes"))
                .thenReturn(PublicoInteres.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(publicoInteres));

        assertEquals(PUBLICO_INTERES_EXISTENTE, excepcion.getMessage());
        verify(publicoInteresRepositorioComando, never()).guardar(any());
    }
}
