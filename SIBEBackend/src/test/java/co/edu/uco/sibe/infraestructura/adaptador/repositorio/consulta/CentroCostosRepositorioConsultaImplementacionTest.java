package co.edu.uco.sibe.infraestructura.adaptador.repositorio.consulta;

import co.edu.uco.sibe.dominio.modelo.CentroCostos;
import co.edu.uco.sibe.infraestructura.adaptador.dao.CentroCostosDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.CentroCostosEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.CentroCostosMapeador;
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
class CentroCostosRepositorioConsultaImplementacionTest {

    @Mock private CentroCostosDAO centroCostosDAO;
    @Mock private CentroCostosMapeador centroCostosMapeador;

    private CentroCostosRepositorioConsultaImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new CentroCostosRepositorioConsultaImplementacion(centroCostosDAO, centroCostosMapeador);
    }

    @Test
    void deberiaRetornarCentroCostosCuandoExisteIdentificador() {
        UUID id = UUID.randomUUID();
        CentroCostosEntidad entidad = new CentroCostosEntidad();
        CentroCostos modelo = mock(CentroCostos.class);

        when(centroCostosDAO.findById(id)).thenReturn(Optional.of(entidad));
        when(centroCostosMapeador.construirModelo(entidad)).thenReturn(modelo);

        CentroCostos resultado = repositorio.consultarPorIdentificador(id);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteIdentificador() {
        UUID id = UUID.randomUUID();

        when(centroCostosDAO.findById(id)).thenReturn(Optional.empty());

        assertNull(repositorio.consultarPorIdentificador(id));
    }

    @Test
    void deberiaRetornarCentroCostosCuandoExisteCodigo() {
        String codigo = "CC001";
        CentroCostosEntidad entidad = new CentroCostosEntidad();
        CentroCostos modelo = mock(CentroCostos.class);

        when(centroCostosDAO.findFirstByCodigo(codigo)).thenReturn(entidad);
        when(centroCostosMapeador.construirModelo(entidad)).thenReturn(modelo);

        CentroCostos resultado = repositorio.consultarPorCodigo(codigo);

        assertNotNull(resultado);
        assertEquals(modelo, resultado);
    }

    @Test
    void deberiaRetornarNuloCuandoNoExisteCodigo() {
        when(centroCostosDAO.findFirstByCodigo("INVALIDO")).thenReturn(null);

        assertNull(repositorio.consultarPorCodigo("INVALIDO"));
    }
}
