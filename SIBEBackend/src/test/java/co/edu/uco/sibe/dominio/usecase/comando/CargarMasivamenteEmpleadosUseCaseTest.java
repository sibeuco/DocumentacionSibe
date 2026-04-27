package co.edu.uco.sibe.dominio.usecase.comando;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.dominio.modelo.RelacionLaboral;
import co.edu.uco.sibe.dominio.puerto.comando.EmpleadoRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.comando.MiembroRepositorioComando;
import co.edu.uco.sibe.dominio.puerto.consulta.EmpleadoRepositorioConsulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CargarMasivamenteEmpleadosUseCaseTest {

    @Mock
    private EmpleadoRepositorioComando empleadoRepositorioComando;
    @Mock
    private EmpleadoRepositorioConsulta empleadoRepositorioConsulta;
    @Mock
    private MiembroRepositorioComando miembroRepositorioComando;

    private CargarMasivamenteEmpleadosUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new CargarMasivamenteEmpleadosUseCase(empleadoRepositorioComando, empleadoRepositorioConsulta, miembroRepositorioComando);
    }

    @Test
    void deberiaGuardarNuevoEmpleadoCuandoNoExiste() {
        UUID identificador = UUID.randomUUID();
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Medellin");
        RelacionLaboral relacion = RelacionLaboral.construir(UUID.randomUUID(), "RL01", "Relacion Laboral");
        CentroCostos centro = CentroCostos.construir(UUID.randomUUID(), "CC001", "Centro Costos");
        Empleado empleado = Empleado.construir(UUID.randomUUID(), "Juan Carlos Lopez", "12345678", ciudad, "CARNET001", "M", relacion, centro);

        when(empleadoRepositorioConsulta.consultarPorIdentificacion(empleado.getNumeroIdentificacion())).thenReturn(null);
        when(empleadoRepositorioComando.guardar(empleado)).thenReturn(identificador);

        UUID resultado = useCase.ejecutar(empleado);

        assertEquals(identificador, resultado);
        verify(empleadoRepositorioComando).guardar(empleado);
        verify(empleadoRepositorioComando, never()).modificar(any(), any());
    }

    @Test
    void deberiaModificarEmpleadoCuandoYaExiste() {
        UUID identificadorExistente = UUID.randomUUID();
        CiudadResidencia ciudad = CiudadResidencia.construir(UUID.randomUUID(), "Medellin");
        RelacionLaboral relacion = RelacionLaboral.construir(UUID.randomUUID(), "RL01", "Relacion Laboral");
        CentroCostos centro = CentroCostos.construir(UUID.randomUUID(), "CC001", "Centro Costos");
        Empleado empleado = Empleado.construir(UUID.randomUUID(), "Juan Carlos Lopez", "12345678", ciudad, "CARNET001", "M", relacion, centro);
        Empleado empleadoExistente = mock(Empleado.class);
        when(empleadoExistente.getIdentificador()).thenReturn(identificadorExistente);

        when(empleadoRepositorioConsulta.consultarPorIdentificacion(empleado.getNumeroIdentificacion())).thenReturn(empleadoExistente);
        when(empleadoRepositorioComando.modificar(empleado, identificadorExistente)).thenReturn(identificadorExistente);

        UUID resultado = useCase.ejecutar(empleado);

        assertEquals(identificadorExistente, resultado);
        verify(empleadoRepositorioComando).modificar(empleado, identificadorExistente);
        verify(empleadoRepositorioComando, never()).guardar(any());
    }
}
