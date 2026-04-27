package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.IdentificacionDTO;
import co.edu.uco.sibe.dominio.dto.TipoIdentificacionDTO;
import co.edu.uco.sibe.dominio.modelo.Identificacion;
import co.edu.uco.sibe.dominio.modelo.TipoIdentificacion;
import co.edu.uco.sibe.infraestructura.adaptador.dao.IdentificacionTipoIdentificacionDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.IdentificacionTipoIdentificacionEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.TipoIdentificacionEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IdentificacionMapeadorTest {

    @Mock
    private TipoIdentificacionMapeador tipoIdentificacionMapeador;

    @Mock
    private IdentificacionTipoIdentificacionDAO identificacionTipoIdentificacionDAO;

    private IdentificacionMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new IdentificacionMapeador(tipoIdentificacionMapeador, identificacionTipoIdentificacionDAO);
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID identificacionId = UUID.randomUUID();
        UUID tipoId = UUID.randomUUID();
        TipoIdentificacionEntidad tipoEntidad = new TipoIdentificacionEntidad(tipoId, "CC", "Cédula");
        IdentificacionTipoIdentificacionEntidad itEntidad = new IdentificacionTipoIdentificacionEntidad(UUID.randomUUID(), tipoEntidad);
        IdentificacionEntidad entidad = new IdentificacionEntidad(identificacionId, "123456789", itEntidad);

        TipoIdentificacion tipoModelo = TipoIdentificacion.construir(tipoId, "CC", "Cédula");
        when(tipoIdentificacionMapeador.construirModelo(tipoEntidad)).thenReturn(tipoModelo);

        Identificacion modelo = mapeador.construirModelo(entidad);

        assertEquals(identificacionId, modelo.getIdentificador());
        assertEquals("123456789", modelo.getNumeroIdentificacion());
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID identificacionId = UUID.randomUUID();
        UUID tipoId = UUID.randomUUID();
        TipoIdentificacion tipo = TipoIdentificacion.construir(tipoId, "PA", "Pasaporte");
        Identificacion identificacion = Identificacion.construir(identificacionId, "987654321", tipo);
        TipoIdentificacionEntidad tipoEntidad = new TipoIdentificacionEntidad(tipoId, "PA", "Pasaporte");

        when(tipoIdentificacionMapeador.construirEntidad(tipo)).thenReturn(tipoEntidad);
        when(identificacionTipoIdentificacionDAO.findById(any(UUID.class))).thenReturn(Optional.empty());

        IdentificacionEntidad entidad = mapeador.construirEntidad(identificacion);

        assertEquals(identificacionId, entidad.getIdentificador());
        assertEquals("987654321", entidad.getNumeroIdentificacion());
    }

    @Test
    void deberiaConstruirDTODesdeEntidad() {
        UUID identificacionId = UUID.randomUUID();
        UUID tipoId = UUID.randomUUID();
        TipoIdentificacionEntidad tipoEntidad = new TipoIdentificacionEntidad(tipoId, "CC", "Cédula");
        IdentificacionTipoIdentificacionEntidad itEntidad = new IdentificacionTipoIdentificacionEntidad(UUID.randomUUID(), tipoEntidad);
        IdentificacionEntidad entidad = new IdentificacionEntidad(identificacionId, "111222333", itEntidad);

        TipoIdentificacionDTO tipoDTO = new TipoIdentificacionDTO(tipoId.toString(), "CC", "Cédula");
        when(tipoIdentificacionMapeador.construirDTO(tipoEntidad)).thenReturn(tipoDTO);

        IdentificacionDTO dto = mapeador.construirDTO(entidad);

        assertEquals(identificacionId.toString(), dto.getIdentificador());
        assertEquals("111222333", dto.getNumeroIdentificacion());
    }

    @Test
    void deberiaModificarEntidad() {
        UUID tipoId = UUID.randomUUID();
        TipoIdentificacionEntidad tipoEntidad = new TipoIdentificacionEntidad(tipoId, "CC", "Cédula");
        IdentificacionTipoIdentificacionEntidad itEntidad = new IdentificacionTipoIdentificacionEntidad(UUID.randomUUID(), tipoEntidad);
        IdentificacionEntidad entidad = new IdentificacionEntidad(UUID.randomUUID(), "OLD-NUM", itEntidad);

        TipoIdentificacion nuevoTipo = TipoIdentificacion.construir(UUID.randomUUID(), "PA", "Pasaporte");
        Identificacion identificacion = Identificacion.construir(UUID.randomUUID(), "NEW-NUM", nuevoTipo);

        mapeador.modificarEntidad(entidad, identificacion);

        assertEquals("NEW-NUM", entidad.getNumeroIdentificacion());
        verify(tipoIdentificacionMapeador).modificarEntidad(eq(itEntidad), eq(nuevoTipo));
    }
}
