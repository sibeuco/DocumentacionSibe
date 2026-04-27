package co.edu.uco.sibe.dominio.usecase.consulta;

import co.edu.uco.sibe.dominio.modelo.Direccion;
import co.edu.uco.sibe.dominio.puerto.consulta.DireccionRepositorioConsulta;
import co.edu.uco.sibe.dominio.service.AutorizacionContextoOrganizacionalServicio;
import co.edu.uco.sibe.dominio.transversal.excepcion.ValorInvalidoExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarDireccionPorNombreUseCaseTest {

    @Mock
    private DireccionRepositorioConsulta direccionRepositorioConsulta;

    @Mock
    private AutorizacionContextoOrganizacionalServicio autorizacionServicio;

    private ConsultarDireccionPorNombreUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ConsultarDireccionPorNombreUseCase(direccionRepositorioConsulta, autorizacionServicio);
    }

    @Test
    void deberiaRetornarDireccionPorNombreExitosamente() {
        Direccion direccion = Direccion.construir();
        when(direccionRepositorioConsulta.consultarPorNombre("Direccion Prueba")).thenReturn(direccion);

        Direccion resultado = useCase.ejecutar("Direccion Prueba");

        assertEquals(direccion, resultado);
    }

    @Test
    void deberiaLanzarExcepcionCuandoDireccionPorNombreNoExiste() {
        when(direccionRepositorioConsulta.consultarPorNombre("NoExiste")).thenReturn(null);

        assertThrows(ValorInvalidoExcepcion.class, () -> useCase.ejecutar("NoExiste"));
    }
}
