package co.edu.uco.sibe.dominio.transversal.utilitarios;

import java.util.UUID;
import java.util.function.Predicate;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

public final class UtilUUID {
    private static final String UUID_DEFECTO_TEXTO = "00000000-0000-0000-0000-000000000000";

    private UtilUUID() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }

    public static UUID textoAUUID(final String texto) {
        return UUID.fromString(obtenerObjetoPorDefecto(texto, UUID_DEFECTO_TEXTO));
    }

    public static UUID obtenerValorDefecto() {
        return textoAUUID(UUID_DEFECTO_TEXTO);
    }

    public static UUID generar(Predicate<UUID> verificadorExistencia) {
        UUID nuevoUUID;
        do {
            nuevoUUID = generarNuevoUUID();
        } while (verificadorExistencia.test(nuevoUUID));

        return nuevoUUID;
    }

    private static UUID generarNuevoUUID() {
        return UUID.randomUUID();
    }
}