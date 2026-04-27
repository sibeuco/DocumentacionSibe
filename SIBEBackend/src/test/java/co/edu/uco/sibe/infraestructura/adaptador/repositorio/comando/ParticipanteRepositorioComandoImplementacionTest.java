package co.edu.uco.sibe.infraestructura.adaptador.repositorio.comando;

import co.edu.uco.sibe.dominio.modelo.Participante;
import co.edu.uco.sibe.infraestructura.adaptador.dao.ParticipanteDAO;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.ParticipanteEntidad;
import co.edu.uco.sibe.infraestructura.adaptador.mapeador.ParticipanteMapeador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipanteRepositorioComandoImplementacionTest {

    @Mock private ParticipanteDAO participanteDAO;
    @Mock private ParticipanteMapeador participanteMapeador;

    private ParticipanteRepositorioComandoImplementacion repositorio;

    @BeforeEach
    void setUp() {
        repositorio = new ParticipanteRepositorioComandoImplementacion(participanteDAO, participanteMapeador);
    }

    @Test
    void deberiaGuardarParticipante() {
        UUID idEsperado = UUID.randomUUID();
        Participante participante = mock(Participante.class);
        ParticipanteEntidad entidad = mock(ParticipanteEntidad.class);

        when(participanteMapeador.construirEntidad(participante)).thenReturn(entidad);
        when(participanteDAO.save(entidad)).thenReturn(entidad);
        when(entidad.getIdentificador()).thenReturn(idEsperado);

        UUID resultado = repositorio.guardar(participante);

        assertEquals(idEsperado, resultado);
    }
}
