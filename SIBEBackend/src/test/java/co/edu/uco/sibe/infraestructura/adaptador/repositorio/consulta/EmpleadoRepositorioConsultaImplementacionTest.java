package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.Empleado;
import co.edu.uco.sibe.infraestructura.adaptador.dao.EmpleadoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EmpleadoEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.EmpleadoMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoRepositorioConsultaImplementacionTest {

    @Mock private EmpleadoDAO empleadoDAO;
    @Mock private EmpleadoMapeador empleadoMapeador;

    private EmpleadoRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new EmpleadoRepositorioConsultaImplementacion(empleadoDAO, empleadoMapeador);
    }

    @Test
    void deberiaRetornarEmpleadoCuandoExisteIdentificador() {
        UUID id = UUID.randomUUID();
        EmpleadoEntidad entidad = new EmpleadoEntidad();
        Empleado modelo = mock(Empleado.class);

        when(empleadoDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(empleadoMapeador.construirModelo(entidad)).thenReturn(modelo);

        Empleado resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificador() {
        UUID id = UUID.randomUUID();

        when(empleadoDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarEmpleadoCuandoExisteIdentificacion() {
        String identificacion = "12345";
        EmpleadoEntidad entidad = new EmpleadoEntidad();
        Empleado modelo = mock(Empleado.class);

        when(empleadoDAO.findFirstByNumeroIdentificacion(identificacion)).thenReturn(entidad);
        when(empleadoMapeador.construirModelo(entidad)).thenReturn(modelo);

        Empleado resultado = repositorio.consultarPorIdentificacion(identificacion);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificacion() {
        when(empleadoDAO.findFirstByNumeroIdentificacion("99999")).thenReturn(null);

        assertNull(repositorio.consultarPorIdentificacion("99999"));
    }
}
