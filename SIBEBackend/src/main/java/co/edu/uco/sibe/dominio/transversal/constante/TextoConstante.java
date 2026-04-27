package co.edu.uco.sibe.dominio.transversal.constante;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;

public final class TextoConstante {
    public static final String VACIO = "";
    public static final String ESPACIO = " ";
    public static final String GUION = "-";
    public static final String UNDERSCORE = "_";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String PUNTO = ".";
    public static final String ASTERISK = "*";
    public static final String IDENTIFICADOR = "identificador";
    public static final String NO_APLICA = "No Aplica";
    public static final String NO_VERIFICADO = "unchecked";
    public static final String TEST_CASE = "test";
    public static final String NUMERO_CERO_UNO = "01";
    public static final String NUMERO_CERO_DOS = "02";

    private TextoConstante() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }
}