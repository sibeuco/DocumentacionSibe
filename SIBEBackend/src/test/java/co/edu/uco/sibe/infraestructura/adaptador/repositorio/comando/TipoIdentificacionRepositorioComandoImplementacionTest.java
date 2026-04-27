package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TipoIdentificacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIdentificacionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TipoIdentificacionMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoIdentificacionRepositorioComandoImplementacionTest {

    @Mock private TipoIdentificacionDAO tipoIdentificacionDAO;
    @Mock private TipoIdentificacionMapeador tipoIdentificacionMapeador;

    private TipoIdentificacionRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TipoIdentificacionRepositorioComandoImplementacion(tipoIdentificacionDAO, tipoIdentificacionMapeador);
    }

    @Test
    void deberiaGuardarTipoIdentificacion() {
        UUID idEsperado = UUID.randomUUID();
        TipoIdentificacion tipoIdentificacion = mock(TipoIdentificacion.class);
        TipoIdentificacionEntidad entidad = mock(TipoIdentificacionEntidad.class);

        when(tipoIdentificacionMapeador.construirEntidad(tipoIdentificacion)).thenReturn(entidad);
        when(tipoIdentificacionDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(tipoIdentificacion);

        assertEquals(idEsperado, resultado);
    }
}
