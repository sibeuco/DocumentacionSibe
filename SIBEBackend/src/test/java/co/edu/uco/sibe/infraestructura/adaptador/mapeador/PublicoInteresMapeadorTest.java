package co.edu.uco.sibe.infraestructura.adaptador.mapeador;

import co.edu.uco.sibe.dominio.dto.PublicoInteresDTO;
import co.edu.uco.sibe.dominio.modelo.PublicoInteres;
import co.edu.uco.sibe.infraestructura.adaptador.entidad.PublicoInteresEntidad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PublicoInteresMapeadorTest {

    private PublicoInteresMapeador mapeador;

    @BeforeEach
    void setUp() {
        mapeador = new PublicoInteresMapeador();
    }

    @Test
    void deberiaConstruirModelo() {
        UUID id = UUID.randomUUID();
        PublicoInteresEntidad entidad = new PublicoInteresEntidad(id, "Estudiantes");
        PublicoInteres modelo = mapeador.construirModelo(entidad);

        assertEquals(id, modelo.getIdentificador());
        assertEquals("Estudiantes", modelo.getNombre());
    }

    @Test
    void deberiaConstruirEntidad() {
        UUID id = UUID.randomUUID();
        PublicoInteres modelo = PublicoInteres.construir(id, "Estudiantes");
        PublicoInteresEntidad entidad = mapeador.construirEntidad(modelo);

        assertEquals(id, entidad.getIdentificador());
    }

    @Test
    void deberiaConstruirDTOs() {
        PublicoInteresEntidad e1 = new PublicoInteresEntidad(UUID.randomUUID(), "Docentes");
        List<PublicoInteresDTO> dtos = mapeador.construirDTOs(List.of(e1));
        assertEquals(1, dtos.size());
    }

    @Test
    void deberiaConstruirModelos() {
        PublicoInteresEntidad e1 = new PublicoInteresEntidad(UUID.randomUUID(), "Estudiantes");
        List<PublicoInteres> modelos = mapeador.construirModelos(List.of(e1));
        assertEquals(1, modelos.size());
    }
}
