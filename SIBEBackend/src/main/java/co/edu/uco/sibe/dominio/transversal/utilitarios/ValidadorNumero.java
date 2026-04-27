package co.edu.uco.sibe.dominio.transversal.utilitarios;

import co.edu.uco.sibe.dominio.transversal.excepcion.LongitudExcepcion;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;
import static co.edu.uco.sibe.dominio.transversal.constante.NumeroConstante.CERO;
import static co.edu.uco.sibe.dominio.transversal.constante.TextoConstante.NO_VERIFICADO;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.esNulo;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

public final class ValidadorNumero {
    private ValidadorNumero() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }

    public static <T extends Number> void validarNumeroEntre(T numero, T minimo, T maximo, String mensaje) {
        if (esNulo(numero) || !esNumeroEntre(numero, minimo, maximo)) {
            throw new LongitudExcepcion(mensaje);
        }
    }

    public static <T extends Number> void validarNumeroMayorOIgual(T numero, T numeroComparador, String mensaje) {
        if (!esNumeroMayorOIgual(numero, numeroComparador)) {
            throw new LongitudExcepcion(mensaje);
        }
    }

    public static <T extends Number> boolean esNumeroEntre(T numero, T limiteInferior, T limiteSuperior) {
        return esNumeroMayorOIgual(numero, limiteInferior) && esNumeroMenorOIgual(numero, limiteSuperior);
    }

    public static <T extends Number> boolean esNumeroMayorOIgual(T numero, T numeroComparador) {
        return esNumeroMayor(numero, numeroComparador) || esNumeroIgual(numero, numeroComparador);
    }

    public static <T extends Number> boolean esNumeroMenorOIgual(T numero, T numeroComparador) {
        return esNumeroMenor(numero, numeroComparador) || esNumeroIgual(numero, numeroComparador);
    }

    public static <T extends Number> boolean esNumeroMayor(T numero, T numeroComparador) {
        return obtenerNumeroPorDefecto(numero).doubleValue() > obtenerNumeroPorDefecto(numeroComparador).doubleValue();
    }

    public static <T extends Number> boolean esNumeroMenor(T numero, T numeroComparador) {
        return obtenerNumeroPorDefecto(numero).doubleValue() < obtenerNumeroPorDefecto(numeroComparador).doubleValue();
    }

    public static <T extends Number> boolean esNumeroIgual(T numero, T numeroComparador) {
        return obtenerNumeroPorDefecto(numero).doubleValue() == obtenerNumeroPorDefecto(numeroComparador).doubleValue();
    }

    @SuppressWarnings(NO_VERIFICADO)
    public static <T extends Number> T obtenerNumeroPorDefecto(T numero) {
        return (T) obtenerObjetoPorDefecto(numero, CERO);
    }
}