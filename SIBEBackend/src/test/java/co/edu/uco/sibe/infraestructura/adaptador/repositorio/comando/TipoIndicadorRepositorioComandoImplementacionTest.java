package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.TipoIndicador;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TipoIndicadorDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIndicadorEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TipoIndicadorMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoIndicadorRepositorioComandoImplementacionTest {

    @Mock private TipoIndicadorDAO tipoIndicadorDAO;
    @Mock private TipoIndicadorMapeador tipoIndicadorMapeador;

    private TipoIndicadorRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TipoIndicadorRepositorioComandoImplementacion(tipoIndicadorDAO, tipoIndicadorMapeador);
    }

    @Test
    void deberiaGuardarTipoIndicador() {
        UUID idEsperado = UUID.randomUUID();
        TipoIndicador tipoIndicador = mock(TipoIndicador.class);
        TipoIndicadorEntidad entidad = mock(TipoIndicadorEntidad.class);

        when(tipoIndicadorMapeador.construirEntidad(tipoIndicador)).thenReturn(entidad);
        when(tipoIndicadorDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(tipoIndicador);

        assertEquals(idEsperado, resultado);
    }
}
