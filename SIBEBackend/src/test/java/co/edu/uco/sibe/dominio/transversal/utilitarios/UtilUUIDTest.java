package co.edu.uco.sibe.dominio.transversal.utilitarios;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class UtilUUIDTest {

    @Test
    void deberiaLanzarExcepcionAlInstanciarClaseUtilitaria() throws Exception {
        var constructor = UtilUUID.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    void deberiaConvertirTextoAUUID() {
        UUID uuid = UUID.randomUUID();
        assertEquals(uuid, UtilUUID.textoAUUID(uuid.toString()));
    }

    @Test
    void deberiaRetornarUUIDDefectoCuandoTextoEsNulo() {
        UUID resultado = UtilUUID.textoAUUID(null);
        assertEquals(UUID.fromString("00000000-0000-0000-0000-000000000000"), resultado);
    }

    @Test
    void deberiaGenerarUUIDUnicoConVerificador() {
        UUID generado = UtilUUID.generar(uuid -> false);
        assertNotNull(generado);
    }

    @Test
    void deberiaReintentarGeneracionCuandoVerificadorRetornaTrue() {
        final int[] intentos = {0};
        UUID generado = UtilUUID.generar(uuid -> {
            intentos[0]++;
            return intentos[0] < 3;
        });
        assertNotNull(generado);
        assertEquals(3, intentos[0]);
    }
}
