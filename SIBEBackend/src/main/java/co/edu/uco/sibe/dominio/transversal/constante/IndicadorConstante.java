package co.edu.uco.sibe.dominio.transversal.constante;

import java.util.Set;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;

public final class IndicadorConstante {

    public static final String INDICADOR_PARTICIPACION = "Participación";
    public static final String INDICADOR_COBERTURA = "Cobertura";
    public static final Set<String> INDICADORES_GLOBALES = Set.of(INDICADOR_PARTICIPACION, INDICADOR_COBERTURA);

    private IndicadorConstante() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }

    public static boolean esIndicadorGlobal(String nombre) {
        if (nombre == null) {
            return false;
        }
        return INDICADORES_GLOBALES.stream()
                .anyMatch(global -> global.equalsIgnoreCase(nombre.trim()));
    }
}
