package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.infraestructura.adaptador.dao.RegistroAsistenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.RegistroAsistenciaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.RegistroAsistenciaMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroAsistenciaRepositorioComandoImplementacionTest {

    @Mock private RegistroAsistenciaDAO registroAsistenciaDAO;
    @Mock private RegistroAsistenciaMapeador registroAsistenciaMapeador;

    private RegistroAsistenciaRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new RegistroAsistenciaRepositorioComandoImplementacion(registroAsistenciaDAO, registroAsistenciaMapeador);
    }

    @Test
    void deberiaGuardarRegistroAsistencia() {
        UUID idEsperado = UUID.randomUUID();
        RegistroAsistencia registro = mock(RegistroAsistencia.class);
        RegistroAsistenciaEntidad entidad = mock(RegistroAsistenciaEntidad.class);

        when(registroAsistenciaMapeador.construirEntidad(registro)).thenReturn(entidad);
        when(registroAsistenciaDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(registro);

        assertEquals(idEsperado, resultado);
    }
}
