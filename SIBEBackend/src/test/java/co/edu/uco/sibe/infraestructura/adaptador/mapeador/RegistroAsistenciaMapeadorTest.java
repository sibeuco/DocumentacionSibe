package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.modelo.RegistroAsistencia;
import co.edu.uco.sibe.dominio.modelo.EjecucionActividad;
import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.EjecucionActividadEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ParticipanteEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.RegistroAsistenciaEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroAsistenciaMapeadorTest {

    @Mock
    private EjecucionActividadMapeador ejecucionActividadMapeador;

    @Mock
    private ParticipanteMapeador participanteMapeador;

    private RegistroAsistenciaMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new RegistroAsistenciaMapeador(ejecucionActividadMapeador, participanteMapeador);
    }

    @Test
    void deberiaConstruirEntidadDesdeModelo() {
        UUID id = UUID.randomUUID();
        EjecucionActividad ejecucion = mock(EjecucionActividad.class);
        Participante participante = mock(Participante.class);
        RegistroAsistencia dominio = RegistroAsistencia.construir(id, ejecucion, participante);

        EjecucionActividadEntidad ejecucionEntidad = mock(EjecucionActividadEntidad.class);
        ParticipanteEntidad participanteEntidad = mock(ParticipanteEntidad.class);

        when(ejecucionActividadMapeador.construirEntidad(ejecucion)).thenReturn(ejecucionEntidad);
        when(participanteMapeador.construirEntidad(participante)).thenReturn(participanteEntidad);

        RegistroAsistenciaEntidad entidad = mapeador.construirEntidad(dominio);

        assertEquals(id, entidad.getIdentificador());
        assertEquals(ejecucionEntidad, entidad.getEjecucionActividad());
        assertEquals(participanteEntidad, entidad.getParticipante());
    }

    @Test
    void deberiaConstruirModeloDesdeEntidad() {
        UUID id = UUID.randomUUID();
        EjecucionActividadEntidad ejecucionEntidad = mock(EjecucionActividadEntidad.class);
        ParticipanteEntidad participanteEntidad = mock(ParticipanteEntidad.class);
        RegistroAsistenciaEntidad entidad = new RegistroAsistenciaEntidad(id, ejecucionEntidad, participanteEntidad);

        EjecucionActividad ejecucion = mock(EjecucionActividad.class);
        Participante participante = mock(Participante.class);

        when(ejecucionActividadMapeador.construirModelo(ejecucionEntidad)).thenReturn(ejecucion);
        when(participanteMapeador.construirModelo(participanteEntidad)).thenReturn(participante);

        RegistroAsistencia modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
    }
}
