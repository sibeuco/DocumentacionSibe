package co.edu.uco.sibe.dominio.transversal.constante;

import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;

public final class NumeroConstante {
    public static final int UNO_NEGATIVO = -1;
    public static final int CERO = 0;
    public static final int UNO = 1;
    public static final int DOS = 2;
    public static final int TRES = 3;
    public static final int CUATRO = 4;
    public static final int CINCO = 5;
    public static final int SEIS = 6;
    public static final int SIETE = 7;
    public static final int OCHO = 8;
    public static final int NUEVE = 9;
    public static final int DIEZ = 10;
    public static final int CUATROCIENTOS = 400;
    public static final int CUATROCIENTOS_UNO = 401;
    public static final int CUATROCIENTOS_TRES = 403;
    public static final int QUINIENTOS = 500;
    public static final int TREINTA_MILLONES = 30000000;

    public static final Long UNO_LONG = 1L;
    public static final Long TRES_MIL_SEICIENTOS_LONG = 3600L;

    private NumeroConstante() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }
}