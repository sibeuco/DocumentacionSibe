package co.edu.uco.sibe.dominio.transversal.utilitarios;

import java.time.LocalDate;
import java.time.LocalTime;
import static co.edu.uco.sibe.dominio.transversal.constante.MensajesSistemaConstante.NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA;
import static co.edu.uco.sibe.dominio.transversal.utilitarios.ValidadorObjeto.obtenerObjetoPorDefecto;

public final class UtilFecha {
    private static final LocalDate FECHA_DEFECTO = LocalDate.of(1900, 1, 1);
    private static final LocalTime HORA_DEFECTO = LocalTime.of(0, 0, 0);

    private UtilFecha() {
        throw new UnsupportedOperationException(NO_SE_PUEDE_INSTANCIAR_UNA_CLASE_UTILITARIA);
    }

    public static LocalDate obtenerFechaActual(){
        return LocalDate.now();
    }

    public static LocalDate obtenerFechaDefecto(){
        return FECHA_DEFECTO;
    }

    public static LocalTime obtenerHoraDefecto(){
        return HORA_DEFECTO;
    }

    public static LocalDate obtenerValorFechaPorDefecto(LocalDate fecha) {
        return obtenerObjetoPorDefecto(fecha, FECHA_DEFECTO);
    }

    public static LocalTime obtenerValorHoraPorDefecto(LocalTime hora) {
        return obtenerObjetoPorDefecto(hora, HORA_DEFECTO);
    }

    public static LocalDate formatearTextoAFecha(String texto) {
        return obtenerValorFechaPorDefecto(LocalDate.parse(texto));
    }
}