package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.dto.MiembroDTO;
import co.edu.uco.sibe.dominio.modelo.Miembro;
import co.edu.uco.sibe.infraestructura.adaptador.dao.InternoDAO;
import co.edu.uco.sibe.infraestructura.adaptador.dao.MiembroDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.InternoEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.MiembroEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.MiembroMapeador;
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
class MiembroRepositorioConsultaImplementacionTest {

    @Mock private MiembroDAO miembroDAO;
    @Mock private InternoDAO internoDAO;
    @Mock private MiembroMapeador miembroMapeador;

    private MiembroRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new MiembroRepositorioConsultaImplementacion(miembroDAO, internoDAO, miembroMapeador);
    }

    @Test
    void deberiaConsultarPorIdentificadorExistente() {
        UUID id = UUID.randomUUID();
        MiembroEntidad entidad = new MiembroEntidad();
        Miembro modelo = mock(Miembro.class);
        when(miembroDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(miembroMapeador.construirModelo(entidad)).thenReturn(modelo);

        Miembro resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificadorNoExiste() {
        UUID id = UUID.randomUUID();
        when(miembroDAO.findById(id)).thenReturn(Optional.empty());

        Miembro resultado = repositorio.consultarPorIdentificador(id);

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorIdentificacionExistente() {
        MiembroEntidad entidad = new MiembroEntidad();
        Miembro modelo = mock(Miembro.class);
        when(miembroDAO.findFirstByNumeroIdentificacionAndEstaActivoTrue("123")).thenReturn(Optional.of(entidad));
        when(miembroMapeador.construirModelo(entidad)).thenReturn(modelo);

        Miembro resultado = repositorio.consultarPorIdentificacion("123");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificacionNoExiste() {
        when(miembroDAO.findFirstByNumeroIdentificacionAndEstaActivoTrue("999")).thenReturn(Optional.empty());

        Miembro resultado = repositorio.consultarPorIdentificacion("999");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorIdentificacionDTOExistente() {
        MiembroEntidad entidad = new MiembroEntidad();
        MiembroDTO dto = new MiembroDTO();
        when(miembroDAO.findFirstByNumeroIdentificacionAndEstaActivoTrue("123")).thenReturn(Optional.of(entidad));
        when(miembroMapeador.construirDTO(entidad)).thenReturn(dto);

        MiembroDTO resultado = repositorio.consultarPorIdentificacionDTO("123");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdentificacionDTONoExiste() {
        when(miembroDAO.findFirstByNumeroIdentificacionAndEstaActivoTrue("999")).thenReturn(Optional.empty());

        MiembroDTO resultado = repositorio.consultarPorIdentificacionDTO("999");

        assertNull(resultado);
    }

    @Test
    void deberiaConsultarPorIdCarnetDTOExistente() {
        InternoEntidad entidad = new InternoEntidad();
        MiembroDTO dto = new MiembroDTO();
        when(internoDAO.findFirstByIdCarnetAndEstaActivoTrue("CARNET1")).thenReturn(Optional.of(entidad));
        when(miembroMapeador.construirDTO(entidad)).thenReturn(dto);

        MiembroDTO resultado = repositorio.consultarPorIdCarnetDTO("CARNET1");

        assertNotNull(resultado);
    }

    @Test
    void deberiaRetornarNullCuandoIdCarnetDTONoExiste() {
        when(internoDAO.findFirstByIdCarnetAndEstaActivoTrue("NOEXISTE")).thenReturn(Optional.empty());

        MiembroDTO resultado = repositorio.consultarPorIdCarnetDTO("NOEXISTE");

        assertNull(resultado);
    }
}
