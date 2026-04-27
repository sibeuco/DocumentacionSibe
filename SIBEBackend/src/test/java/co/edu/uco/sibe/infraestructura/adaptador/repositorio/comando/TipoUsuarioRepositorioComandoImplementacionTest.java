package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.TipoUsuario;
import co.edu.uco.sibe.infraestructura.adaptador.dao.TipoUsuarioDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoUsuarioEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.TipoUsuarioMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoUsuarioRepositorioComandoImplementacionTest {

    @Mock private TipoUsuarioDAO tipoUsuarioDAO;
    @Mock private TipoUsuarioMapeador tipoUsuarioMapeador;

    private TipoUsuarioRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new TipoUsuarioRepositorioComandoImplementacion(tipoUsuarioDAO, tipoUsuarioMapeador);
    }

    @Test
    void deberiaGuardarTipoUsuario() {
        UUID idEsperado = UUID.randomUUID();
        TipoUsuario tipoUsuario = mock(TipoUsuario.class);
        TipoUsuarioEntidad entidad = mock(TipoUsuarioEntidad.class);

        when(tipoUsuarioMapeador.construirEntidad(tipoUsuario)).thenReturn(entidad);
        when(tipoUsuarioDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(tipoUsuario);

        assertEquals(idEsperado, resultado);
    }
}
