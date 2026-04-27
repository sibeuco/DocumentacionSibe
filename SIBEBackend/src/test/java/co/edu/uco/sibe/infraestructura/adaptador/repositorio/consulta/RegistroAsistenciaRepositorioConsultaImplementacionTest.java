package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RegistroAsistenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.RegistroAsistenciaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.RegistroAsistenciaMapeador;
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
class RegistroAsistenciaRepositorioConsultaImplementacionTest {

    @Mock private RegistroAsistenciaDAO registroAsistenciaDAO;
    @Mock private RegistroAsistenciaMapeador registroAsistenciaMapeador;

    private RegistroAsistenciaRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new RegistroAsistenciaRepositorioConsultaImplementacion(registroAsistenciaDAO, registroAsistenciaMapeador);
    }

    @Test
    void deberiaRetornarRegistroCuandoExiste() {
        UUID id = UUID.randomUUID();
        RegistroAsistenciaEntidad entidad = new RegistroAsistenciaEntidad();
        RegistroAsistencia modelo = mock(RegistroAsistencia.class);

        when(registroAsistenciaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(registroAsistenciaMapeador.construirModelo(entidad)).thenReturn(modelo);

        RegistroAsistencia resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExiste() {
        UUID id = UUID.randomUUID();

        when(registroAsistenciaDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }
}
