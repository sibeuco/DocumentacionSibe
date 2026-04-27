package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Temporalidad;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TemporalidadDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TemporalidadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TemporalidadMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemporalidadRepositorioComandoImplementacionTest {

    @Mock private TemporalidadDAO temporalidadDAO;
    @Mock private TemporalidadMapeador temporalidadMapeador;

    private TemporalidadRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TemporalidadRepositorioComandoImplementacion(temporalidadDAO, temporalidadMapeador);
    }

    @Test
    void deberiaGuardarTemporalidad() {
        UUID idEsperado = UUID.randomUUID();
        Temporalidad temporalidad = mock(Temporalidad.class);
        TemporalidadEntidad entidad = mock(TemporalidadEntidad.class);

        when(temporalidadMapeador.construirEntidad(temporalidad)).thenReturn(entidad);
        when(temporalidadDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(temporalidad);

        assertEquals(idEsperado, resultado);
    }
}
