package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IdentificacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.IdentificacionMapeador;
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
class IdentificacionRepositorioConsultaImplementacionTest {

    @Mock private IdentificacionDAO identificacionDAO;
    @Mock private IdentificacionMapeador identificacionMapeador;

    private IdentificacionRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new IdentificacionRepositorioConsultaImplementacion(identificacionDAO, identificacionMapeador);
    }

    @Test
    void deberiaRetornarIdentificacionCuandoExisteIdentificador() {
        UUID id = UUID.randomUUID();
        IdentificacionEntidad entidad = new IdentificacionEntidad();
        Identificacion modelo = mock(Identificacion.class);

        when(identificacionDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(identificacionMapeador.construirModelo(entidad)).thenReturn(modelo);

        Identificacion resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificador() {
        UUID id = UUID.randomUUID();

        when(identificacionDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarIdentificacionCuandoExisteNumero() {
        String numero = "1234567890";
        IdentificacionEntidad entidad = new IdentificacionEntidad();
        Identificacion modelo = mock(Identificacion.class);

        when(identificacionDAO.findFirstByNumeroIdentificacion(numero)).thenReturn(entidad);
        when(identificacionMapeador.construirModelo(entidad)).thenReturn(modelo);

        Identificacion resultado = repositorio.consultarPorNumeroIdentificacion(numero);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteNumeroIdentificacion() {
        when(identificacionDAO.findFirstByNumeroIdentificacion("0000")).thenReturn(null);

        assertNull(repositorio.consultarPorNumeroIdentificacion("0000"));
    }
}
