package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.CiudadResidencia;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CiudadResidenciaDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.CiudadResidenciaEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.CiudadResidenciaMapeador;
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
class CiudadResidenciaRepositorioConsultaImplementacionTest {

    @Mock private CiudadResidenciaDAO ciudadResidenciaDAO;
    @Mock private CiudadResidenciaMapeador ciudadResidenciaMapeador;

    private CiudadResidenciaRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new CiudadResidenciaRepositorioConsultaImplementacion(ciudadResidenciaDAO, ciudadResidenciaMapeador);
    }

    @Test
    void deberiaRetornarCiudadCuandoExisteIdentificador() {
        UUID id = UUID.randomUUID();
        CiudadResidenciaEntidad entidad = new CiudadResidenciaEntidad();
        CiudadResidencia modelo = mock(CiudadResidencia.class);

        when(ciudadResidenciaDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(ciudadResidenciaMapeador.construirModelo(entidad)).thenReturn(modelo);

        CiudadResidencia resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificador() {
        UUID id = UUID.randomUUID();

        when(ciudadResidenciaDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarCiudadCuandoExisteDescripcion() {
        String descripcion = "Medellín";
        CiudadResidenciaEntidad entidad = new CiudadResidenciaEntidad();
        CiudadResidencia modelo = mock(CiudadResidencia.class);

        when(ciudadResidenciaDAO.findFirstByDescripcion(descripcion)).thenReturn(entidad);
        when(ciudadResidenciaMapeador.construirModelo(entidad)).thenReturn(modelo);

        CiudadResidencia resultado = repositorio.consultarPorDescripcion(descripcion);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteDescripcion() {
        when(ciudadResidenciaDAO.findFirstByDescripcion("Inexistente")).thenReturn(null);

        assertNull(repositorio.consultarPorDescripcion("Inexistente"));
    }
}
