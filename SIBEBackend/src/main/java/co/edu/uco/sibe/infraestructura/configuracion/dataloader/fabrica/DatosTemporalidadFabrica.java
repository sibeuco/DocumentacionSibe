package co.edu.uco.sibe.infraestructura.configuracion.dataloader.fabrica;

import java.util.List;
import static co.edu.uco.sibe.dominio.transversal.constante.DatoConstante.*;

public final class DatosTemporalidadFabrica {
    private DatosTemporalidadFabrica() {
        super();
    }

    public static List<String> obtenerTemporalidades() {
        return List.of(
                TEMPORALIDAD_DIARIA,
                TEMPORALIDAD_SEMANAL,
                TEMPORALIDAD_MENSUAL,
                TEMPORALIDAD_TRIMESTRAL,
                TEMPORALIDAD_SEMESTRAL,
                TEMPORALIDAD_ANUAL
        );
    }
}