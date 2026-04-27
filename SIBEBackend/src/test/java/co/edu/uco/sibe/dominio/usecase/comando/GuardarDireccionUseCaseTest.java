package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.puerto.comando.DireccionRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorDuplicadoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesErrorConstante.NOMBRE_DIRECCION_EXISTENTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuardarDireccionUseCaseTest {

    @Mock
    private DireccionRepositorioComando direccionRepositorioComando;
    @Mock
    private DireccionRepositorioConsulta direccionRepositorioConsulta;

    private GuardarDireccionUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new GuardarDireccionUseCase(direccionRepositorioComando, direccionRepositorioConsulta);
    }

    @Test
    void deberiaGuardarDireccionExitosamente() {
        UUID identificador = UUID.randomUUID();
        Direccion direccion = Direccion.construir(UUID.randomUUID(), "Bienestar Evangelizacion", new ArrayList<>(), new ArrayList<>());

        when(direccionRepositorioConsulta.consultarPorNombre("Bienestar Evangelizacion")).thenReturn(null);
        when(direccionRepositorioComando.guardar(direccion)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(direccion);

        assertEquals(identificador, resultado);
    }

    @Test
    void deberiaLanzarValorDuplicadoExcepcionCuandoNombreExiste() {
        Direccion direccion = Direccion.construir(UUID.randomUUID(), "Bienestar Evangelizacion", new ArrayList<>(), new ArrayList<>());

        when(direccionRepositorioConsulta.consultarPorNombre("Bienestar Evangelizacion"))
                .thenReturn(Direccion.construir());

        ValorDuplicadoExcepcion excepcion = assertThrows(ValorDuplicadoExcepcion.class,
                () -> useCase.ejecutar(direccion));

        assertEquals(NOMBRE_DIRECCION_EXISTENTE, excepcion.getMessage());
        verify(direccionRepositorioComando, never()).guardar(any());
    }
}
