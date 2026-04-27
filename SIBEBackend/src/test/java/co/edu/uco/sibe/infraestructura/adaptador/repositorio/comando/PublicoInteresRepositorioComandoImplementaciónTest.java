package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.infraestructura.adaptador.dao.PublicoInteresDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PublicoInteresEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.PublicoInteresMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PublicoInteresRepositorioComandoImplementaciónTest {

    @Mock private PublicoInteresDAO publicoInteresDAO;
    @Mock private PublicoInteresMapeador publicoInteresMapeador;

    private PublicoInteresRepositorioComandoImplementación repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new PublicoInteresRepositorioComandoImplementación(publicoInteresDAO, publicoInteresMapeador);
    }

    @Test
    void deberiaGuardarPublicoInteres() {
        UUID idEsperado = UUID.randomUUID();
        PublicoInteres publicoInteres = mock(PublicoInteres.class);
        PublicoInteresEntidad entidad = mock(PublicoInteresEntidad.class);

        when(publicoInteresMapeador.construirEntidad(publicoInteres)).thenReturn(entidad);
        when(publicoInteresDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(publicoInteres);

        assertEquals(idEsperado, resultado);
    }
}
