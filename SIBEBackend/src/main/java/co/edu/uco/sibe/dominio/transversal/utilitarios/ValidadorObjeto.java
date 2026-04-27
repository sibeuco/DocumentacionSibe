package co.edu.uco.sibe.dominio.transversal.utilitarios;

import java.util.Collection;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;

public final class ValidadorObjeto {
    private ValidadorObjeto() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }

    public static <T> void validarObligatorio(T objeto, String mensaje) {
        if (esNulo(objeto)) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    public static <T extends Collection<?>> void validarColeccionNoVacia(T coleccion, String mensaje) {
        if (esNulo(coleccion) || coleccion.isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }

    public static <T> boolean esNulo(T objeto) {
        return objeto == null;
    }

    public static <T> T obtenerObjetoPorDefecto(T objeto, T valorPorDefecto) {
        return esNulo(objeto) ? valorPorDefecto : objeto;
    }
}