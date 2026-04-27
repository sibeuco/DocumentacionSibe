package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Indicador;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IndicadorDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IndicadorEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.IndicadorMapeador;
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
class IndicadorRepositorioComandoImplementacionTest {

    @Mock private IndicadorDAO indicadorDAO;
    @Mock private IndicadorMapeador indicadorMapeador;

    private IndicadorRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new IndicadorRepositorioComandoImplementacion(indicadorDAO, indicadorMapeador);
    }

    @Test
    void deberiaGuardarIndicador() {
        UUID idEsperado = UUID.randomUUID();
        Indicador indicador = mock(Indicador.class);
        IndicadorEntidad entidad = mock(IndicadorEntidad.class);

        when(indicadorMapeador.construirEntidad(indicador)).thenReturn(entidad);
        when(indicadorDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(indicador);

        assertEquals(idEsperado, resultado);
    }

    @Test
    void deberiaModificarIndicador() {
        UUID id = UUID.randomUUID();
        UUID idEsperado = UUID.randomUUID();
        Indicador indicador = mock(Indicador.class);
        IndicadorEntidad entidad = mock(IndicadorEntidad.class);

        when(indicadorDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(indicadorDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.modificar(indicador, id);

        assertEquals(idEsperado, resultado);
        verify(indicadorMapeador).actualizarEntidad(entidad, indicador);
    }
}
